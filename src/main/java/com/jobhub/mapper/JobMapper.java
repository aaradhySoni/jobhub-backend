package com.jobhub.mapper;

import com.jobhub.dto.request.JobRequestDTO;
import com.jobhub.dto.response.JobResponseDTO;
import com.jobhub.entity.Job;
import com.jobhub.entity.RecruiterProfile;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public Job toEntity(
            JobRequestDTO request,
            RecruiterProfile recruiterProfile) {

        return Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .responsibilities(request.getResponsibilities())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .employmentType(request.getEmploymentType())
                .experienceLevel(request.getExperienceLevel())
                .salaryMin(request.getSalaryMin())
                .salaryMax(request.getSalaryMax())
                .currency(request.getCurrency())
                .vacancies(request.getVacancies())
                .applicationDeadline(request.getApplicationDeadline())
                .recruiterProfile(recruiterProfile)
                .build();
    }

    public JobResponseDTO toResponse(Job job) {

        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .city(job.getCity())
                .state(job.getState())
                .country(job.getCountry())
                .employmentType(job.getEmploymentType())
                .experienceLevel(job.getExperienceLevel())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .currency(job.getCurrency())
                .vacancies(job.getVacancies())
                .applicationDeadline(job.getApplicationDeadline())
                .status(job.getStatus())

                // Recruiter Details

                .recruiterName(job.getRecruiterProfile()
                        .getUser()
                        .getName())

                .companyName(job.getRecruiterProfile()
                        .getCompanyName())

                .companyLogoUrl(job.getRecruiterProfile()
                        .getCompanyLogoUrl())

                .postedAt(job.getPostedAt())

                .build();
    }

    public void updateEntity(
            Job job,
            JobRequestDTO request) {

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setResponsibilities(request.getResponsibilities());

        job.setCity(request.getCity());
        job.setState(request.getState());
        job.setCountry(request.getCountry());

        job.setEmploymentType(request.getEmploymentType());
        job.setExperienceLevel(request.getExperienceLevel());

        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());

        job.setCurrency(request.getCurrency());

        job.setVacancies(request.getVacancies());

        job.setApplicationDeadline(request.getApplicationDeadline());
    }
}