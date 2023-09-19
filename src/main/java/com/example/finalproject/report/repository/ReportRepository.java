package com.example.finalproject.report.repository;

import com.example.finalproject.report.entity.ApprovalReport;
import com.example.finalproject.report.entity.Report;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<Report> getAllUnApprovalReport();

    @Query("SELECT r FROM Report r " +
            "WHERE ST_DWithin(r.point, :route, 10000.0) = true")
    List<Report> findActiveReportsForUserRoute(@Param("route") LineString route);

    @Query(value = "SELECT r " +
            "FROM Report r " +
            "LEFT JOIN ApprovalReport ar ON r.id = ar.id " +
            "WHERE r.id = :reportId " +
            "AND r.deleteStatus = false " +
            "AND (ar IS NULL OR ar.approvalStatus = 'PENDING')")
    Optional<Report> getReportById(@Param("reportId") Long reportId);

    @Query("SELECT HOUR(r.creationTime) AS hour, COUNT(r) AS accidentCount " +
            "FROM Accident r " +
            "GROUP BY HOUR(r.creationTime) " +
            "ORDER BY accidentCount DESC " +
            "LIMIT 1")
    List<Object[]> findTopAccidentHours();


    @Query(value = "UPDATE Report r " +
            "SET r.deleteStatus = true " +
            "WHERE r.expiredTime = :expiredTime " +
            "AND (SELECT ar FROM ApprovalReport ar WHERE ar.id = r.id) IS NULL OR " +
            "(SELECT ar.approvalStatus FROM ApprovalReport ar WHERE ar.id = r.id) = 'CONFIRMED')")
    void removeReportExpired();
}
