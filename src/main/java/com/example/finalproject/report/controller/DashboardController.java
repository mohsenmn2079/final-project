package com.example.finalproject.report.controller;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.service.ReportService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/dashboard/reports/unapproved")
public class DashboardController {
    ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportDto>> unapproved() {
        List<ReportDto> reportList = reportService.getAllUnApprovalReport();
        return ResponseEntity.ok(reportList);
    }

    //  API endpoint for report approval by operators
    @PostMapping("/{reportId}")
    public ResponseEntity<String> approveReport(@PathVariable Long reportId) {
        reportService.approveReport(reportId);
        return ResponseEntity.ok("Report approved successfully");
    }
}
