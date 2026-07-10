package com.jobhub.controller;

import com.jobhub.dto.response.SavedJobResponseDTO;
import com.jobhub.service.service.SavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-jobs")
@RequiredArgsConstructor
public class SavedJobController {

    private final SavedJobService savedJobService;
//save a jon
    @PostMapping("/{jobId}")
    public ResponseEntity<String> saveJob(
            @PathVariable Long jobId) {

        savedJobService.saveJob(jobId);

        return ResponseEntity.ok("Job saved successfully.");
    }
// view saved job
    @GetMapping
    public ResponseEntity<Page<SavedJobResponseDTO>> getMySavedJobs(
            @PageableDefault(size = 10)
            Pageable pageable) {

        return ResponseEntity.ok(
                savedJobService.getMySavedJobs(pageable));
    }
// remove saved job
    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> removeSavedJob(
            @PathVariable Long jobId) {

        savedJobService.removeSavedJob(jobId);

        return ResponseEntity.ok("Saved job removed successfully.");
    }
//check if job is saved
    @GetMapping("/check/{jobId}")
    public ResponseEntity<Boolean> isJobSaved(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                savedJobService.isJobSaved(jobId));
    }
}