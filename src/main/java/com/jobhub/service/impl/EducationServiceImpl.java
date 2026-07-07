package com.jobhub.service.impl;

import com.jobhub.dto.request.EducationRequestDTO;
import com.jobhub.dto.response.EducationResponseDTO;
import com.jobhub.entity.Education;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.mapper.EducationMapper;
import com.jobhub.repository.EducationRepository;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;
    private final EducationMapper educationMapper;

    @Override
    public EducationResponseDTO addEducation(EducationRequestDTO request) {

        validateEducation(request);

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Education education = educationMapper.toEntity(request, profile);

        Education savedEducation = educationRepository.save(education);

        return educationMapper.toResponse(savedEducation);
    }

    @Override
    public List<EducationResponseDTO> getMyEducations() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return educationRepository.findByJobSeekerProfile(profile)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    @Override
    public EducationResponseDTO updateEducation(Long educationId,
                                                EducationRequestDTO request) {

        validateEducation(request);

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new RuntimeException("Education not found."));

        if (!education.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this education.");
        }

        educationMapper.updateEntity(education, request);

        Education updatedEducation =
                educationRepository.save(education);

        return educationMapper.toResponse(updatedEducation);
    }

    @Override
    public void deleteEducation(Long educationId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new RuntimeException("Education not found."));

        if (!education.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this education.");
        }

        educationRepository.delete(education);
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

    private void validateEducation(EducationRequestDTO request) {

        if (request.getCurrentlyStudying()
                && request.getEndDate() != null) {

            throw new RuntimeException(
                    "End date must be null if currently studying.");
        }

        if (!request.getCurrentlyStudying()
                && request.getEndDate() == null) {

            throw new RuntimeException(
                    "End date is required if education is completed.");
        }
    }
}