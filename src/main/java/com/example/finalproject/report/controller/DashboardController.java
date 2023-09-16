package com.example.finalproject.report.controller;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.entity.Accident;
import com.example.finalproject.report.entity.Report;
import com.example.finalproject.report.mapper.ReportMapper;
import com.example.finalproject.report.service.ReportService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/dashboard/reports/unapproved")
public class DashboardController {
    ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportDto>> unapproved() {
        List<ReportDto> reportList = reportService.getAllApprovalReport();
        return ResponseEntity.ok(reportList);
    }

    //  API endpoint for report approval by operators
//    @PostMapping("/approve/{reportId}")
//    public ResponseEntity<String> approveReport(@PathVariable Long reportId) {
//        reportService.approveReport(reportId);
//        return ResponseEntity.ok("Report approved successfully");
//    }
}
