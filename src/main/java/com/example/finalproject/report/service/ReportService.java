package com.example.finalproject.report.service;

import com.example.finalproject.report.Dto.ReportDto;
import com.example.finalproject.report.Dto.RouteDto;
import com.example.finalproject.report.config.ReportConfig;
import com.example.finalproject.report.entity.*;
import com.example.finalproject.report.exeption.LikeDislikeException;
import com.example.finalproject.report.exeption.ReportNotFoundException;
import com.example.finalproject.report.mapper.AccidentMapper;
import com.example.finalproject.report.mapper.ReportMapper;
import com.example.finalproject.report.mapper.TrafficMapper;
import com.example.finalproject.report.repository.AccidentRepository;
import com.example.finalproject.report.repository.ApprovalReportRepository;
import com.example.finalproject.report.repository.ReportRepository;
import com.example.finalproject.report.repository.TrafficRepository;
import com.example.finalproject.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(2);

        String lockKey = "report_creation_lock_" + user.getId() + "_" + reportDto.getPoint();
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

    public void likeReport(Long reportId, Long userId) {
        RLock lock = redissonClient.getLock("report-lock:" + reportId);

        try {
            lock.lock();

            Report report = reportRepository.getReportById(reportId)
                    .orElseThrow(() -> new ReportNotFoundException(reportId));

            if (report.getLikes() == null) {
                report.setLikes(new HashSet<>());
            }else
            if(report.getDislikes().contains(userId)||report.getLikes().contains(userId)){
                throw new LikeDislikeException(userId,reportId);
            }
            report.getLikes().add(userId);
            report.setExpiredTime(LocalDateTime.now().plusMinutes(1));

            reportRepository.save(report);
        } finally {
            lock.unlock();
        }
    }

    public void dislikeReport(Long reportId, Long userId) {
        RLock lock = redissonClient.getLock("report-lock:" + reportId);
        try {
            lock.lock();

            Report report = reportRepository.getReportById(reportId)
                    .orElseThrow(() -> new ReportNotFoundException(reportId));

            if (report.getLikes() == null) {
                report.setLikes(new HashSet<>());
            }else if(report.getDislikes().contains(userId)||report.getLikes().contains(userId)){
                throw new LikeDislikeException(userId,reportId);
            }
            report.getDislikes().add(userId);
            report.setExpiredTime(LocalDateTime.now().minusMinutes(1));

            reportRepository.save(report);
        } finally {
            lock.unlock();
        }
    }


    public List<ReportDto> getAllUnApprovalReport() {
        return reportRepository.getAllUnApprovalReport()
                .orElseThrow(() -> new ReportNotFoundException("There are no reports around here"))
                .stream()
                .map(ReportMapper::ToDto)
                .toList();
    }
    public void approveReport(Long reportId) {
        ApprovalReport report = approvalReportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));

        report.setApprovalStatus(ApprovalStatus.CONFIRMED);
        report.setExpiredTime(LocalDateTime.now().plusMinutes(2));
        reportRepository.save(report);
    }



    public List<ReportDto> getReportsForUserRoute(RouteDto routeDto) {
        System.out.println(routeDto.getRoute());
        return reportRepository
                .getReportsForUserRoute(routeDto.getRoute())
                .orElseThrow(() -> new ReportNotFoundException("There are no reports around here"))
                .stream()
                .map(ReportMapper::ToDto)
                .toList();
    }

    public Long getTopAccidentHour() {
        return reportRepository.findTopAccidentHours();
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void removeExpiredReport() {
        reportRepository.removeReportExpired();
    }

}

