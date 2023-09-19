package com.example.finalproject.report.service;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.Dto.RouteDto;
import com.example.finalproject.report.entity.*;
import com.example.finalproject.report.exeption.ReportNotFoundException;
import com.example.finalproject.report.mapper.AccidentMapper;
import com.example.finalproject.report.mapper.ReportMapper;
import com.example.finalproject.report.mapper.TrafficMapper;
import com.example.finalproject.report.repository.AccidentRepository;
import com.example.finalproject.report.repository.ApprovalReportRepository;
import com.example.finalproject.report.repository.ReportRepository;
import com.example.finalproject.report.repository.TrafficRepository;
import com.example.finalproject.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.GeometryFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportService {

    ReportRepository reportRepository;
    AccidentRepository accidentRepository;
    TrafficRepository trafficRepository;
    ApprovalReportRepository approvalReportRepository;
    RedissonClient redissonClient;

    public void submitReport(ReportDto reportDto, User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoffTime = now.minusMinutes(2);
        String lockKey = "report_creation_lock_" + user.getId();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isLocked = lock.tryLock(10, TimeUnit.SECONDS);
            if (isLocked) {
                if (
                        reportRepository.existsReportByLocationAndExpiredAt(user.getId(), reportDto.getPoint(), cutoffTime)
                ) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Duplicated request.");
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }


    public List<ReportDto> getAllUnApprovalReport() {
        return reportRepository.getAllUnApprovalReport()
                .stream()
                .map(ReportMapper::ToDto)
                .toList();
    }


    public void likeReport(Long reportId) {
        RLock lock = redissonClient.getLock("report-lock:" + reportId);

        try {
            lock.lock();

            Report report = reportRepository.getReportById(reportId)
                    .orElseThrow(() -> new ReportNotFoundException(reportId));

            report.setLikes(report.getLikes() + 1);
            report.setExpiredTime(LocalDateTime.now().plusMinutes(1));

            reportRepository.save(report);
        } finally {
            lock.unlock();
        }
    }

    public void dislikeReport(Long reportId) {
        RLock lock = redissonClient.getLock("report-lock:" + reportId);
        try {
            lock.lock();

            Report report = reportRepository.getReportById(reportId)
                    .orElseThrow(() -> new ReportNotFoundException(reportId));

            report.setLikes(report.getLikes() + 1);
            report.setExpiredTime(LocalDateTime.now().minusMinutes(1));

            reportRepository.save(report);
        } finally {
            lock.unlock();
        }
    }

    public void approveReport(Long reportId) {
        ApprovalReport report = approvalReportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));

        report.setApprovalStatus(ApprovalStatus.CONFIRMED);
        report.setExpiredTime(LocalDateTime.now().plusMinutes(2));
        reportRepository.save(report);
    }

    public List<ReportDto> getReportsForUserRoute(RouteDto routeDto, User user) {
        return reportRepository
                .findActiveReportsForUserRoute(routeDto.getRoute())
                .stream()
                .map(ReportMapper::ToDto)
                .toList();
    }
    public List<Object[]> getTopAccidentHour() {
        return reportRepository.findTopAccidentHours();
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredReport(){
        reportRepository.removeReportExpired();
    }

}

