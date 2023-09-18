package com.example.finalproject.report.controller;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.Dto.RouteDto;
import com.example.finalproject.report.service.ReportService;
import com.example.finalproject.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/app/reports")
public class AppController {

    ReportService reportService;

//     API endpoint for report submission
    @PostMapping("/submit")
    public ResponseEntity<String> submitReport(Authentication authentication, @RequestBody ReportDto reportDto) {
        User user = ((User) authentication.getPrincipal());
        reportService.submitReport(reportDto, user);
        return ResponseEntity.ok("Report submitted successfully");
    }

//     API endpoint for liking a report
    @PostMapping("/like/{reportId}")
    public ResponseEntity<?> likeReport(@PathVariable Long reportId) {
        reportService.likeReport(reportId);
        return ResponseEntity.ok("Report liked");
    }

//     API endpoint for disliking a report
    @PostMapping("/dislike/{reportId}")
    public ResponseEntity<?> dislikeReport(@PathVariable Long reportId) {
        reportService.dislikeReport(reportId);
        return ResponseEntity.ok("Report disliked");
    }

    //     API endpoint for retrieving active reports for a user's route
    @PostMapping ("/routing")
    public ResponseEntity<?> getActiveReportsForUserRoute(Authentication authentication, @RequestBody RouteDto routeDto) {
        User user = ((User) authentication.getPrincipal());
        List<ReportDto> activeReports = reportService.getActiveReportsForUserRoute(routeDto,user);
        return ResponseEntity.ok(activeReports);
    }
}
