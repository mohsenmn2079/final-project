package com.example.finalproject.report.repository;

import com.example.finalproject.report.entity.ApprovalReport;
import com.example.finalproject.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r WHERE TYPE(r) = Accident ")
    List<Report> getAllApprovalReport();
}
