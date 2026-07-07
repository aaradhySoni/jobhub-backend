package com.jobhub.mapper;


import com.jobhub.dto.request.ExperienceRequestDTO;
import com.jobhub.dto.response.ExperienceResponseDTO;
import com.jobhub.entity.Experience;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    // DTO -> Entity
    public Experience toEntity(
            ExperienceRequestDTO request,
            JobSeekerProfile profile) {

        return Experience.builder()
                .jobTitle(request.getJobTitle())
                .companyName(request.getCompanyName())
                .employmentType(request.getEmploymentType())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .currentlyWorking(request.getCurrentlyWorking())
                .description(request.getDescription())
                .jobSeekerProfile(profile)
                .build();
    }

    // Entity -> DTO
    public ExperienceResponseDTO toResponse(
            Experience experience) {

        return ExperienceResponseDTO.builder()
                .id(experience.getId())
                .jobTitle(experience.getJobTitle())
                .companyName(experience.getCompanyName())
                .employmentType(experience.getEmploymentType())
                .location(experience.getLocation())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .currentlyWorking(experience.getCurrentlyWorking())
                .description(experience.getDescription())
                .build();
    }

    // Update Existing Entity
    public void updateEntity(
            Experience experience,
            ExperienceRequestDTO request) {

        experience.setJobTitle(request.getJobTitle());
        experience.setCompanyName(request.getCompanyName());
        experience.setEmploymentType(request.getEmploymentType());
        experience.setLocation(request.getLocation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrentlyWorking(request.getCurrentlyWorking());
        experience.setDescription(request.getDescription());
    }
}