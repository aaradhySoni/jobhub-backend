package com.jobhub.mapper;


import com.jobhub.dto.request.EducationRequestDTO;
import com.jobhub.dto.response.EducationResponseDTO;
import com.jobhub.entity.Education;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    // DTO -> Entity
    public Education toEntity(EducationRequestDTO request,
                              JobSeekerProfile profile) {

        return Education.builder()
                .institutionName(request.getInstitutionName())
                .degree(request.getDegree())
                .fieldOfStudy(request.getFieldOfStudy())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .percentage(request.getPercentage())
                .description(request.getDescription())
                .currentlyStudying(request.getCurrentlyStudying())
                .jobSeekerProfile(profile)
                .build();
    }

    // Entity -> DTO
    public EducationResponseDTO toResponse(Education education) {

        return EducationResponseDTO.builder()
                .id(education.getId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .percentage(education.getPercentage())
                .description(education.getDescription())
                .currentlyStudying(education.getCurrentlyStudying())
                .build();
    }

    // Update Existing Entity
    public void updateEntity(Education education,
                             EducationRequestDTO request) {

        education.setInstitutionName(request.getInstitutionName());
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setPercentage(request.getPercentage());
        education.setDescription(request.getDescription());
        education.setCurrentlyStudying(request.getCurrentlyStudying());
    }
}
