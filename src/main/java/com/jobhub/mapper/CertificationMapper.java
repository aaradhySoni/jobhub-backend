package com.jobhub.mapper;

import com.jobhub.dto.request.CertificationRequestDTO;
import com.jobhub.dto.response.CertificationResponseDTO;
import com.jobhub.entity.Certification;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.stereotype.Component;

@Component
public class CertificationMapper {

    public Certification toEntity(CertificationRequestDTO request, JobSeekerProfile profile) {

        return Certification.builder()
                .name(request.getName())
                .issuingOrganization(request.getIssuingOrganization())
                .issueDate(request.getIssueDate())
                .expiryDate(request.getExpiryDate())
                .jobSeekerProfile(profile)
                .build();
    }

    public CertificationResponseDTO toResponse(Certification certification) {

        return CertificationResponseDTO.builder()
                .id(certification.getId())
                .name(certification.getName())
                .issuingOrganization(certification.getIssuingOrganization())
                .issueDate(certification.getIssueDate())
                .expiryDate(certification.getExpiryDate())
                .certificateFileUrl(certification.getCertificateFileUrl())
                .build();
    }

    public void updateEntity(Certification certification, CertificationRequestDTO request) {

        certification.setName(request.getName());
        certification.setIssuingOrganization(request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setExpiryDate(request.getExpiryDate());
    }
}