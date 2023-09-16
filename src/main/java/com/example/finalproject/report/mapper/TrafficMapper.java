package com.example.finalproject.report.mapper;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.entity.Accident;
import com.example.finalproject.report.entity.Traffic;
import com.example.finalproject.user.entity.User;
import org.locationtech.jts.geom.Point;

public class TrafficMapper {
    public static Traffic toEntity(ReportDto reportDto, User user) {
        return new Traffic(
                reportDto.getTitle()
                , reportDto.getDescription()
                , user
                , reportDto.getPoint()
        );
    }
}
