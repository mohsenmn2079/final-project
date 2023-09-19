package com.example.finalproject.report.controller;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.Dto.RouteDto;
import com.example.finalproject.report.service.ReportService;
import com.example.finalproject.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    public ResponseEntity<String> likeReport(@PathVariable Long reportId, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        reportService.likeReport(reportId,user.getId());
        return ResponseEntity.ok("Report liked");
    }

    //     API endpoint for disliking a report
    @PostMapping("/dislike/{reportId}")

    public ResponseEntity<String> dislikeReport(@PathVariable Long reportId, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        reportService.dislikeReport(reportId,user.getId());
        return ResponseEntity.ok("Report disliked");
    }

    //     API endpoint for retrieving active reports for a user's route
    @PostMapping("/routing")
    public ResponseEntity<List<ReportDto>> getReportsForUserRoute(@RequestBody RouteDto routeDto) {
        List<ReportDto> activeReports = reportService.getReportsForUserRoute(routeDto);
        return ResponseEntity.ok(activeReports);
    }

    // API endpoint for retrieving top accident hour
    @GetMapping("/top-accident-hour")
    public ResponseEntity<String> getTopAccidentHour() {
        return ResponseEntity.ok("top accident hour :" + reportService.getTopAccidentHour());
    }
}
