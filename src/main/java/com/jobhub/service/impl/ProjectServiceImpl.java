package com.jobhub.service.impl;

import com.jobhub.dto.request.ProjectRequestDTO;
import com.jobhub.dto.response.ProjectResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.Project;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.mapper.ProjectMapper;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.ProjectRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDTO addProject(ProjectRequestDTO request) {

        validateProject(request);

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Project project = projectMapper.toEntity(request, profile);

        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponse(savedProject);
    }

    @Override
    public List<ProjectResponseDTO> getMyProjects() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return projectRepository.findByJobSeekerProfile(profile)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponseDTO updateProject(
            Long projectId,
            ProjectRequestDTO request) {

        validateProject(request);

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found."));

        if (!project.getJobSeekerProfile().getId().equals(profile.getId())) {
            throw new RuntimeException(
                    "You are not allowed to update this project.");
        }

        projectMapper.updateEntity(project, request);

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toResponse(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found."));

        if (!project.getJobSeekerProfile().getId().equals(profile.getId())) {
            throw new RuntimeException(
                    "You are not allowed to delete this project.");
        }

        projectRepository.delete(project);
    }

    // ================= Helper Methods =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private JobSeekerProfile getLoggedInJobSeekerProfile() {

        User user = getLoggedInUser();

        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException(
                    "Only job seekers can perform this operation.");
        }

        return jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Job seeker profile not found."));
    }

    private void validateProject(ProjectRequestDTO request) {

        if (request.getCurrentlyWorking()
                && request.getEndDate() != null) {

            throw new RuntimeException(
                    "End date must be null if project is currently in progress.");
        }

        if (!request.getCurrentlyWorking()
                && request.getEndDate() == null) {

            throw new RuntimeException(
                    "End date is required if project is completed.");
        }
    }
}