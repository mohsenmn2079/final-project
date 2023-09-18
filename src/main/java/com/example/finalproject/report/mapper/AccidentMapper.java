package com.example.finalproject.report.mapper;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.entity.Accident;
import com.example.finalproject.report.entity.Report;
import com.example.finalproject.user.entity.User;
import org.springframework.data.geo.Point;

public class AccidentMapper {
    public static Accident toEntity(ReportDto reportDto, User user) {
        Accident accident = new Accident();
        accident.setTitle(reportDto.getTitle());
        accident.setDescription(reportDto.getDescription());
        accident.setUser(user);
        accident.setPoint(reportDto.getPoint());
        return accident;
    }
}
