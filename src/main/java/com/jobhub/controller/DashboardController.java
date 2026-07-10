package com.jobhub.controller;

import com.jobhub.dto.response.JobSeekerDashboardResponseDTO;
import com.jobhub.dto.response.RecentApplicationResponseDTO;
import com.jobhub.dto.response.RecruiterDashboardResponseDTO;
import com.jobhub.service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/recruiter")
    public ResponseEntity<RecruiterDashboardResponseDTO>
    getRecruiterDashboard() {

        return ResponseEntity.ok(
                dashboardService.getRecruiterDashboard());
    }

    @GetMapping("/recruiter/recent")
    public ResponseEntity<List<RecentApplicationResponseDTO>>
    getRecentApplications() {

        return ResponseEntity.ok(
                dashboardService.getRecentApplications());
    }
// for job seeker dashboard
    @GetMapping("/jobseeker")
    public ResponseEntity<JobSeekerDashboardResponseDTO>
    getJobSeekerDashboard() {

        return ResponseEntity.ok(
                dashboardService.getJobSeekerDashboard());
    }
}