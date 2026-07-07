package com.jobhub.service.service;

import com.jobhub.dto.request.CertificationRequestDTO;
import com.jobhub.dto.response.CertificationResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CertificationService {

    CertificationResponseDTO addCertification(CertificationRequestDTO request);

    List<CertificationResponseDTO> getMyCertifications();

    CertificationResponseDTO updateCertification(Long certificationId, CertificationRequestDTO request);

    void deleteCertification(Long certificationId);

    String uploadCertificate(Long certificationId, MultipartFile file);
}