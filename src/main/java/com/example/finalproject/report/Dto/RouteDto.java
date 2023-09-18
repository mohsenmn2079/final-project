package com.example.finalproject.report.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.LineString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteDto {
    LineString route;
}
