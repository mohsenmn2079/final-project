package com.example.finalproject.report.controller;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.service.ReportService;
import com.example.finalproject.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/app/reports")
public class AppController {

    ReportService reportService;

//     API endpoint for report submission
    @PostMapping("/submit")
    public ResponseEntity<?> submitReport(Authentication authentication, @RequestBody ReportDto reportDto) {
        User user = ((User) authentication.getPrincipal());
        reportService.submitReport(reportDto, user);
        return ResponseEntity.ok("Report submitted successfully");
    }


//
//    // API endpoint for retrieving active reports for a user's route
//    @GetMapping("/active-reports")
//    public ResponseEntity<?> getActiveReportsForUserRoute(@RequestParam Double startLat, @RequestParam Double startLng, @RequestParam Double endLat, @RequestParam Double endLng) {
//        // Retrieve and return active reports for the user's route
//        List<ReportDto> activeReports = reportService.getActiveReportsForUserRoute(startLat, startLng, endLat, endLng);
//        return ResponseEntity.ok(activeReports);
//    }
//
//    // API endpoint for liking a report
//    @PostMapping("/like/{reportId}")
//    public ResponseEntity<?> likeReport(@PathVariable Long reportId) {
//        // Process the report liking
//        reportService.likeReport(reportId);
//        return ResponseEntity.ok("Report liked");
//    }
//
//    // API endpoint for disliking a report
//    @PostMapping("/dislike/{reportId}")
//    public ResponseEntity<?> dislikeReport(@PathVariable Long reportId) {
//        // Process the report disliking
//        reportService.dislikeReport(reportId);
//        return ResponseEntity.ok("Report disliked");
//    }
}
