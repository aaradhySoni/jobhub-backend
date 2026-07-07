package com.jobhub.service.impl;

import com.jobhub.dto.request.ExperienceRequestDTO;
import com.jobhub.dto.response.ExperienceResponseDTO;
import com.jobhub.entity.Experience;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.mapper.ExperienceMapper;
import com.jobhub.repository.ExperienceRepository;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    // DI
    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    @Override
    public ExperienceResponseDTO addExperience(ExperienceRequestDTO request) {

        validateExperience(request);

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Experience experience = experienceMapper.toEntity(request, profile);

        Experience savedExperience = experienceRepository.save(experience);

        return experienceMapper.toResponse(savedExperience);
    }

    @Override
    public List<ExperienceResponseDTO> getMyExperiences() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return experienceRepository.findByJobSeekerProfile(profile)
                .stream()
                .map(experienceMapper::toResponse)
                .toList();
    }

    @Override
    public ExperienceResponseDTO updateExperience(
            Long experienceId,
            ExperienceRequestDTO request) {

        validateExperience(request);

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() ->
                        new RuntimeException("Experience not found."));

        if (!experience.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this experience.");
        }

        experienceMapper.updateEntity(experience, request);

        Experience updatedExperience =
                experienceRepository.save(experience);

        return experienceMapper.toResponse(updatedExperience);
    }

    @Override
    public void deleteExperience(Long experienceId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() ->
                        new RuntimeException("Experience not found."));

        if (!experience.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this experience.");
        }

        experienceRepository.delete(experience);
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

    private void validateExperience(ExperienceRequestDTO request) {

        if (request.getCurrentlyWorking()
                && request.getEndDate() != null) {

            throw new RuntimeException(
                    "End date must be null if currently working.");
        }

        if (!request.getCurrentlyWorking()
                && request.getEndDate() == null) {

            throw new RuntimeException(
                    "End date is required if experience is completed.");
        }
    }
}