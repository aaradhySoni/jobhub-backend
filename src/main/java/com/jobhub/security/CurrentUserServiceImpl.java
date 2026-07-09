package com.jobhub.security;

import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.RecruiterProfileRepository;
import com.jobhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    private final RecruiterProfileRepository recruiterProfileRepository;

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));
    }

    @Override
    public JobSeekerProfile getCurrentJobSeeker() {

        User user = getCurrentUser();

        if (user.getRole() != Role.JOB_SEEKER) {

            throw new RuntimeException(
                    "Only Job Seekers can perform this operation.");
        }

        return jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job Seeker Profile not found."));
    }

    @Override
    public RecruiterProfile getCurrentRecruiter() {

        User user = getCurrentUser();

        if (user.getRole() != Role.RECRUITER) {

            throw new RuntimeException(
                    "Only Recruiters can perform this operation.");
        }

        return recruiterProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter Profile not found."));
    }
}