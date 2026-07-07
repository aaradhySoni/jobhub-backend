package com.jobhub.mapper;

import com.jobhub.dto.request.ProjectRequestDTO;
import com.jobhub.dto.response.ProjectResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    // DTO -> Entity
    public Project toEntity(ProjectRequestDTO request,
                            JobSeekerProfile profile) {

        return Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .technologiesUsed(request.getTechnologiesUsed())
                .projectUrl(request.getProjectUrl())
                .githubUrl(request.getGithubUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .currentlyWorking(request.getCurrentlyWorking())
                .jobSeekerProfile(profile)
                .build();
    }

    // Entity -> DTO
    public ProjectResponseDTO toResponse(Project project) {

        return ProjectResponseDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .technologiesUsed(project.getTechnologiesUsed())
                .projectUrl(project.getProjectUrl())
                .githubUrl(project.getGithubUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .currentlyWorking(project.getCurrentlyWorking())
                .build();
    }

    // Update Entity
    public void updateEntity(Project project,
                             ProjectRequestDTO request) {

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechnologiesUsed(request.getTechnologiesUsed());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setCurrentlyWorking(request.getCurrentlyWorking());
    }
}