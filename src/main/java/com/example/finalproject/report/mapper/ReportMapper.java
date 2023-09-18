package com.example.finalproject.report.mapper;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.entity.Report;
import com.example.finalproject.user.entity.User;
import org.springframework.data.geo.Point;

public class ReportMapper {
    public static Report toEntity(ReportDto reportDto, User user) {
        Report report = new Report();
        report.setTitle(reportDto.getTitle());
        report.setDescription(reportDto.getDescription());
        report.setUser(user);
        report.setPoint(reportDto.getPoint());
        return report;
    }

    public static ReportDto ToDto(Report report) {
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportDto.setTitle(report.getTitle());
        reportDto.setDescription(report.getDescription());
        reportDto.setPoint(report.getPoint());
        reportDto.setReportType(report);
        return reportDto;
    }

}
