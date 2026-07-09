package com.jobhub.mapper;

import com.jobhub.dto.response.ApplicantResponseDTO;
import com.jobhub.dto.response.JobApplicationResponseDTO;
import com.jobhub.entity.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapper {

    public JobApplicationResponseDTO toResponseDTO(JobApplication jobApplication) {

        return JobApplicationResponseDTO.builder()
                .applicationId(jobApplication.getId())
                .jobId(jobApplication.getJob().getId())
                .jobTitle(jobApplication.getJob().getTitle())
                .companyName(jobApplication.getJob().getRecruiterProfile().getCompanyName())
                .city(jobApplication.getJob().getCity())
                .state(jobApplication.getJob().getState())
                .country(jobApplication.getJob().getCountry())
                .status(jobApplication.getStatus())
                .appliedAt(jobApplication.getCreatedAt())
                .build();
    }
    public ApplicantResponseDTO toApplicantResponseDTO(
            JobApplication jobApplication) {

        return ApplicantResponseDTO.builder()
                .applicationId(jobApplication.getId())
                .applicantId(jobApplication.getJobSeekerProfile().getId())
                .applicantName(jobApplication.getJobSeekerProfile()
                        .getUser()
                        .getName())
                .headline(jobApplication.getJobSeekerProfile()
                        .getHeadline())
                .city(jobApplication.getJobSeekerProfile()
                        .getCity())
                .state(jobApplication.getJobSeekerProfile()
                        .getState())
                .country(jobApplication.getJobSeekerProfile()
                        .getCountry())
                .resumeUrl(jobApplication.getJobSeekerProfile()
                        .getResumeUrl())
                .coverLetterUrl(jobApplication.getJobSeekerProfile()
                        .getCoverLetterUrl())
                .status(jobApplication.getStatus())
                .appliedAt(jobApplication.getCreatedAt())
                .build();
    }
}