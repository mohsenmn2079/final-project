package com.example.finalproject.report.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
@Getter
public class ReportConfig {
    @Value("${report.expiredTime}")
    public static long DURATION_TIME;
    @Value("${report.incrementTime}")
    public static long INCREMENT_TIME;
    @Value("${report.decrementTime}")
    public static long DECREMENT_TIME;

}
