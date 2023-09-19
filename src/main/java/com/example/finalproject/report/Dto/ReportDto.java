package com.example.finalproject.report.Dto;

import com.example.finalproject.report.entity.Report;
import com.example.finalproject.report.entity.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ReportDto implements Serializable {
    Long id;
    String title;
    String description;
    Point point;
    ReportType type;
    public void setReportType(Report report){
        switch (report.getClass().getSimpleName()) {
            case "Accident" -> {
                this.type=ReportType.TYPE_ACCIDENT;
            }
            case "Traffic" -> {
                this.type=ReportType.TYPE_TRAFFIC;
            }
        }
    }
}
