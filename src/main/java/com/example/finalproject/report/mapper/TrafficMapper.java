package com.example.finalproject.report.mapper;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.entity.Accident;
import com.example.finalproject.report.entity.Traffic;
import com.example.finalproject.user.entity.User;
import org.locationtech.jts.geom.Point;

public class TrafficMapper {
    public static Traffic toEntity(ReportDto reportDto, User user) {
        Traffic traffic = new Traffic();
        traffic.setTitle(reportDto.getTitle());
        traffic.setDescription(reportDto.getDescription());
        traffic.setUser(user);
        traffic.setPoint(reportDto.getPoint());
        return traffic;
    }
}
