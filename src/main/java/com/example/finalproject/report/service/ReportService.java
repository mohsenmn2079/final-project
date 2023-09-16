package com.example.finalproject.report.service;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.entity.*;
import com.example.finalproject.report.mapper.AccidentMapper;
import com.example.finalproject.report.mapper.ReportMapper;
import com.example.finalproject.report.mapper.TrafficMapper;
import com.example.finalproject.report.repository.AccidentRepository;
import com.example.finalproject.report.repository.ReportRepository;
import com.example.finalproject.report.repository.TrafficRepository;
import com.example.finalproject.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportService {

    ReportRepository reportRepository;
    AccidentRepository accidentRepository;
    TrafficRepository trafficRepository;

    public void submitReport(ReportDto reportDto, User user) {
        switch (reportDto.getType()) {
            case TYPE_ACCIDENT -> {
                Accident accident = AccidentMapper.toEntity(reportDto, user);
                accidentRepository.save(accident);
            }
            case TYPE_TRAFFIC -> {
                Traffic traffic = TrafficMapper.toEntity(reportDto, user);
                trafficRepository.save(traffic);
            }
        }
    }

    public List<ReportDto> getAllApprovalReport(){
        System.out.println("enter service");
        accidentRepository.findAll();
//        List<ReportDto> list = reportRepository.getAllApprovalReport()
//                .stream()
//                .map(ReportMapper::ToDto)
//                .toList();
        System.out.println("back to controller");

        return null;

    }
//    public void approveReport(Long reportId) {
//        Report report = reportRepository.findById(reportId)
//                .orElseThrow(() -> new ReportNotFoundException(reportId));
//
//        if (report.getReportType().isOperatorApprovalRequired()) {
//            // Perform operator approval actions
//            // ...
//        }
//
//        // Update the approval status
//        report.setApprovalStatus(ApprovalStatus.APPROVED);
//
//        // Save the updated report
//        reportRepository.save(report);
//    }

//    public List<ReportDto> getActiveReportsForUserRoute(Double startLat, Double startLng, Double endLat, Double endLng) {
//        // Implement logic to retrieve active reports for the user's route
//        // You can use the start and end coordinates to query the database
//        // and return a list of ReportDto objects
//        // ...
//        List<Report> activeReports = reportRepository.findActiveReportsForUserRoute(startLat, startLng, endLat, endLng);
//        return convertToReportDtoList(activeReports);
//    }
//
//    @Transactional
//    public void likeReport(Long reportId) {
//        // Implement report liking logic
//        // Fetch the report by ID from the database
//        Report report = reportRepository.findById(reportId)
//                .orElseThrow(() -> new ReportNotFoundException(reportId));
//
//        // Increment the likes count
//        report.setLikes(report.getLikes() + 1);
//
//        // Update the report's activation time (if needed)
//        // ...
//
//        // Save the updated report
//        reportRepository.save(report);
//    }
//
//    @Transactional
//    public void dislikeReport(Long reportId) {
//        // Implement report disliking logic
//        // Fetch the report by ID from the database
//        Report report = reportRepository.findById(reportId)
//                .orElseThrow(() -> new ReportNotFoundException(reportId));
//
//        // Decrement the dislikes count
//        report.setDislikes(report.getDislikes() + 1);
//
//        // Update the report's activation time (if needed)
//        // ...
//
//        // Save the updated report
//        reportRepository.save(report);
//    }
//
//    // Helper method to convert Report entities to ReportDto objects
//    private List<ReportDto> convertToReportDtoList(List<Report> reports) {
//        // Implement conversion logic
//        // ...
//    }
}

