package com.jobhub.controller;

import com.jobhub.dto.request.EducationRequestDTO;
import com.jobhub.dto.response.EducationResponseDTO;
import com.jobhub.service.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponseDTO> addEducation(
            @Valid @RequestBody EducationRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(educationService.addEducation(request));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponseDTO>> getMyEducations() {

        return ResponseEntity.ok(
                educationService.getMyEducations());
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponseDTO> updateEducation(
            @PathVariable Long educationId,
            @Valid @RequestBody EducationRequestDTO request) {

        return ResponseEntity.ok(
                educationService.updateEducation(educationId, request));
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<String> deleteEducation(
            @PathVariable Long educationId) {

        educationService.deleteEducation(educationId);

        return ResponseEntity.ok("Education deleted successfully.");
    }
}