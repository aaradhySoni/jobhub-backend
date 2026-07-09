package com.jobhub.controller;

//import com.jobhub.dto.request.ApplicationStatusUpdateRequestDTO;
//import com.jobhub.dto.request.JobApplicationResponseDTO;
import com.jobhub.dto.request.ApplicationStatusRequestDTO;
import com.jobhub.dto.response.ApplicantResponseDTO;
import com.jobhub.dto.response.JobApplicationResponseDTO;
import com.jobhub.service.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    // ================= Apply Job =================

    @PostMapping("/jobs/{jobId}")

    public ResponseEntity<String> applyToJob(
            @PathVariable Long jobId) {
        System.out.println("Apply Job API Hit");
        jobApplicationService.applyToJob(jobId);

        return ResponseEntity.ok(
                "Job application submitted successfully.");
    }

    // ================= My Applications =================

    @GetMapping("/my")
    public ResponseEntity<Page<JobApplicationResponseDTO>> getMyApplications(

            @PageableDefault(size = 10)
            Pageable pageable) {

        return ResponseEntity.ok(
                jobApplicationService.getMyApplications(pageable));
    }

    // ================= Recruiter View Applicants =================

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Page<ApplicantResponseDTO>> getApplicantsForJob(

            @PathVariable Long jobId,

            @PageableDefault(size = 10)
            Pageable pageable) {

        return ResponseEntity.ok(
                jobApplicationService.getApplicantsForJob(
                        jobId,
                        pageable));
    }

    // ================= Update Status =================

    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(

            @PathVariable Long applicationId,

            @Valid
            @RequestBody
            ApplicationStatusRequestDTO request) {

        jobApplicationService.updateApplicationStatus(
                applicationId,
                request.getStatus());

        return ResponseEntity.ok(
                "Application status updated successfully.");
    }
}