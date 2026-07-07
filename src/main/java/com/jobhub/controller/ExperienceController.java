package com.jobhub.controller;

import com.jobhub.dto.request.ExperienceRequestDTO;
import com.jobhub.dto.response.ExperienceResponseDTO;
import com.jobhub.service.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ExperienceResponseDTO> addExperience(
            @Valid @RequestBody ExperienceRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experienceService.addExperience(request));
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponseDTO>> getMyExperiences() {

        return ResponseEntity.ok(
                experienceService.getMyExperiences());
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponseDTO> updateExperience(
            @PathVariable Long experienceId,
            @Valid @RequestBody ExperienceRequestDTO request) {

        return ResponseEntity.ok(
                experienceService.updateExperience(
                        experienceId,
                        request));
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<String> deleteExperience(
            @PathVariable Long experienceId) {

        experienceService.deleteExperience(experienceId);

        return ResponseEntity.ok("Experience deleted successfully.");
    }
}