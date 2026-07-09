package com.jobhub.service.service;

import com.jobhub.dto.response.ApplicantResponseDTO;
import com.jobhub.dto.response.JobApplicationResponseDTO;
import com.jobhub.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobApplicationService {

    void applyToJob(Long jobId);

    Page<JobApplicationResponseDTO> getMyApplications(Pageable pageable);

    Page<ApplicantResponseDTO> getApplicantsForJob(
            Long jobId,
            Pageable pageable
    );

    void updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status
    );
}