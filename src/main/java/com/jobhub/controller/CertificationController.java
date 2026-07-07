package com.jobhub.controller;

import com.jobhub.dto.request.CertificationRequestDTO;
import com.jobhub.dto.response.CertificationResponseDTO;
import com.jobhub.service.service.CertificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping
    public ResponseEntity<CertificationResponseDTO> addCertification(
            @Valid @RequestBody CertificationRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(certificationService.addCertification(request));
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponseDTO>> getMyCertifications() {

        return ResponseEntity.ok(
                certificationService.getMyCertifications());
    }

    @PutMapping("/{certificationId}")
    public ResponseEntity<CertificationResponseDTO> updateCertification(
            @PathVariable Long certificationId,
            @Valid @RequestBody CertificationRequestDTO request) {

        return ResponseEntity.ok(
                certificationService.updateCertification(
                        certificationId,
                        request));
    }

    @DeleteMapping("/{certificationId}")
    public ResponseEntity<String> deleteCertification(
            @PathVariable Long certificationId) {

        certificationService.deleteCertification(certificationId);

        return ResponseEntity.ok(
                "Certification deleted successfully.");
    }
    @PostMapping("/{certificationId}/upload")
    public ResponseEntity<String> uploadCertificate(
            @PathVariable Long certificationId,
            @RequestParam("file") MultipartFile file) {

        System.out.println("Upload API Hit");

        return ResponseEntity.ok(
                certificationService.uploadCertificate(certificationId, file));
    }
}