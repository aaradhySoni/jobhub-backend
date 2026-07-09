package com.jobhub.controller;

import com.jobhub.dto.request.JobRequestDTO;
import com.jobhub.dto.response.JobResponseDTO;
import com.jobhub.enums.EmploymentType;
import com.jobhub.enums.ExperienceLevel;
import com.jobhub.service.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // ================= Recruiter APIs =================

    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(
            @Valid @RequestBody JobRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.createJob(request));
    }
//should be accessed only by admin
    // recruiter should be able to see candidate profile
        // one recruiter can update their job only
    @GetMapping("/my")
    public ResponseEntity<List<JobResponseDTO>> getMyJobs() {

        return ResponseEntity.ok(
                jobService.getMyJobs());
    }

    @GetMapping("/my/{jobId}")
    public ResponseEntity<JobResponseDTO> getMyJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                jobService.getMyJob(jobId));
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponseDTO> updateJob(
            @PathVariable Long jobId,
            @Valid @RequestBody JobRequestDTO request) {

        return ResponseEntity.ok(
                jobService.updateJob(jobId, request));
    }

    @PatchMapping("/{jobId}/publish")
    public ResponseEntity<String> publishJob(
            @PathVariable Long jobId) {

        jobService.publishJob(jobId);

        return ResponseEntity.ok("Job published successfully.");

    }

    @PatchMapping("/{jobId}/close")
    public ResponseEntity<String> closeJob(
            @PathVariable Long jobId) {

        jobService.closeJob(jobId);

        return ResponseEntity.ok("Job closed successfully.");
    }

    // ================= Public APIs =================
    @GetMapping
    public ResponseEntity<Page<JobResponseDTO>> getJobs(

            @RequestParam(required = false) String title,

            @RequestParam(required = false) String city,

            @RequestParam(required = false)
            EmploymentType employmentType,

            @RequestParam(required = false)
            ExperienceLevel experienceLevel,

            Pageable pageable) {

        return ResponseEntity.ok(
                jobService.getJobs(
                        title,
                        city,
                        employmentType,
                        experienceLevel,
                        pageable));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponseDTO> getJobById(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                jobService.getJobById(jobId));
    }
}