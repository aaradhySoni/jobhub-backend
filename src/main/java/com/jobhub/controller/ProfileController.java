package com.jobhub.controller;

import com.jobhub.dto.request.JobSeekerProfileRequestDTO;
import com.jobhub.dto.request.RecruiterProfileRequestDTO;
import com.jobhub.dto.response.JobSeekerProfileResponseDTO;
import com.jobhub.dto.response.RecruiterProfileResponseDTO;
//import com.jobhub.service..service.ProfileService;
import com.jobhub.service.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // ================= Job Seeker =================

    @PostMapping("/jobseeker")
    public ResponseEntity<JobSeekerProfileResponseDTO> createJobSeekerProfile(
            @Valid @RequestBody JobSeekerProfileRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileService.createJobSeekerProfile(request));
    }

    @GetMapping("/jobseeker")
    public ResponseEntity<JobSeekerProfileResponseDTO> getMyJobSeekerProfile() {

        return ResponseEntity.ok(
                profileService.getMyJobSeekerProfile());
    }

    @PutMapping("/jobseeker")
    public ResponseEntity<JobSeekerProfileResponseDTO> updateJobSeekerProfile(
            @Valid @RequestBody JobSeekerProfileRequestDTO request) {

        return ResponseEntity.ok(
                profileService.updateJobSeekerProfile(request));
    }
    @PostMapping("/jobseeker/resume/upload")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                profileService.uploadResume(file));
    }
    @DeleteMapping("/jobseeker/resume")
    public ResponseEntity<String> deleteResume() {

        profileService.deleteResume();

        return ResponseEntity.ok(
                "Resume deleted successfully.");
    }
    @PostMapping("/jobseeker/cover-letter/upload")
    public ResponseEntity<String> uploadCoverLetter(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                profileService.uploadCoverLetter(file));
    }
    @DeleteMapping("/jobseeker/cover-letter")
    public ResponseEntity<String> deleteCoverLetter() {

        profileService.deleteCoverLetter();

        return ResponseEntity.ok(
                "Cover Letter deleted successfully.");
    }

    // ================= Recruiter =================

    @PostMapping("/recruiter")
    public ResponseEntity<RecruiterProfileResponseDTO> createRecruiterProfile(
            @Valid @RequestBody RecruiterProfileRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileService.createRecruiterProfile(request));
    }

    @GetMapping("/recruiter")
    public ResponseEntity<RecruiterProfileResponseDTO> getMyRecruiterProfile() {

        return ResponseEntity.ok(
                profileService.getMyRecruiterProfile());
    }

    @PutMapping("/recruiter")
    public ResponseEntity<RecruiterProfileResponseDTO> updateRecruiterProfile(
            @Valid @RequestBody RecruiterProfileRequestDTO request) {

        return ResponseEntity.ok(
                profileService.updateRecruiterProfile(request));
    }


}
