package com.jobhub.mapper;

import com.jobhub.dto.response.SavedJobResponseDTO;
import com.jobhub.entity.SavedJob;
import org.springframework.stereotype.Component;

@Component
public class SavedJobMapper {

    public SavedJobResponseDTO toResponseDTO(SavedJob savedJob) {

        return SavedJobResponseDTO.builder()
                .savedJobId(savedJob.getId())
                .jobId(savedJob.getJob().getId())
                .jobTitle(savedJob.getJob().getTitle())
                .companyName(savedJob.getJob()
                        .getRecruiterProfile()
                        .getCompanyName())
                .city(savedJob.getJob().getCity())
                .state(savedJob.getJob().getState())
                .country(savedJob.getJob().getCountry())
                .employmentType(savedJob.getJob().getEmploymentType())
                .salaryMin(savedJob.getJob().getSalaryMin())
                .salaryMax(savedJob.getJob().getSalaryMax())
                .currency(savedJob.getJob().getCurrency())
                .savedAt(savedJob.getCreatedAt())
                .build();
    }
}

/*
Why no RequestDTO?

Because the request is simply:

POST /api/saved-jobs/15

The only input is:

jobId → @PathVariable
Logged-in user → JWT

Nothing else is required.
*/