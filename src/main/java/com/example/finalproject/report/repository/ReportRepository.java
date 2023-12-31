package com.example.finalproject.report.repository;

import com.example.finalproject.report.entity.Report;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Report r " +
            "WHERE r.user.id = :userId " +
            "AND r.point = :point " +
            "AND r.creationTime >= :cutoffTime " +
            "AND r.deleteStatus = false")
    boolean existsReportByLocationAndExpiredAt(@Param("userId") Long userId,
                                               @Param("point") Point point,
                                               @Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT r FROM ApprovalReport r " +
            "WHERE r.approvalStatus = 'PENDING' " +
            "AND r.deleteStatus=false")
    Optional<List<Report>> getAllUnApprovalReport();

    @Query(value = "SELECT r " +
            "FROM Report r " +
            "LEFT JOIN ApprovalReport ar ON r.id = ar.id " +
            "WHERE r.id = :reportId " +
            "AND r.deleteStatus = false " +
            "AND (ar IS NULL OR ar.approvalStatus = 'PENDING')")
    Optional<Report> getReportById(@Param("reportId") Long reportId);

    @Query("SELECT r FROM Report r " +
            "LEFT JOIN ApprovalReport ar ON r.id = ar.id " +
            "WHERE r.deleteStatus = false " +
            "AND (ar IS NULL OR ar.approvalStatus = 'CONFIRMED')" +
            "AND ST_DWithin(r.point, :route, 10.0 ,false ) = true")

    Optional<List<Report>> getReportsForUserRoute(@Param("route") LineString route);

    @Modifying
    @Query("UPDATE Report r " +
            "SET r.deleteStatus = true " +
            "WHERE r.expiredTime <= CURRENT_TIMESTAMP " +
            "AND (SELECT COUNT(ar) FROM ApprovalReport ar WHERE ar.id = r.id) = 0 " +
            "OR (SELECT COUNT(ar) FROM ApprovalReport ar WHERE ar.id = r.id AND ar.approvalStatus = 'CONFIRMED') > 0")
    void removeReportExpired();

    @Query("SELECT HOUR(r.creationTime) AS hour, COUNT(r) AS accidentCount " +
            "FROM Accident r " +
            "GROUP BY HOUR(r.creationTime) " +
            "ORDER BY accidentCount DESC " +
            "LIMIT 1")
    Long findTopAccidentHours();

}
