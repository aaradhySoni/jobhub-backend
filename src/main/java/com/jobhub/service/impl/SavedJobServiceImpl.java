package com.jobhub.service.impl;

import com.jobhub.dto.response.SavedJobResponseDTO;
import com.jobhub.entity.Job;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.SavedJob;
import com.jobhub.entity.User;
import com.jobhub.enums.JobStatus;
import com.jobhub.enums.Role;
import com.jobhub.mapper.SavedJobMapper;
import com.jobhub.repository.JobRepository;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.SavedJobRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.SavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;
    private final SavedJobMapper savedJobMapper;

    @Override
    public void saveJob(Long jobId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Job job = getJob(jobId);

        if (job.getStatus() != JobStatus.OPEN) {
            throw new RuntimeException("Job is not available.");
        }

        if (savedJobRepository.existsByJobAndJobSeekerProfile(job, profile)) {
            throw new RuntimeException("Job already saved.");
        }

        SavedJob savedJob = SavedJob.builder()
                .job(job)
                .jobSeekerProfile(profile)
                .build();

        savedJobRepository.save(savedJob);
    }

    @Override
    public Page<SavedJobResponseDTO> getMySavedJobs(Pageable pageable) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return savedJobRepository.findByJobSeekerProfile(profile, pageable)
                .map(savedJobMapper::toResponseDTO);
    }

    @Override
    public void removeSavedJob(Long jobId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Job job = getJob(jobId);

        SavedJob savedJob = savedJobRepository
                .findByJobAndJobSeekerProfile(job, profile)
                .orElseThrow(() ->
                        new RuntimeException("Saved job not found."));

        savedJobRepository.delete(savedJob);
    }

    @Override
    public boolean isJobSaved(Long jobId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Job job = getJob(jobId);

        return savedJobRepository.existsByJobAndJobSeekerProfile(job, profile);
    }

    // ================= Helper Methods =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found."));
    }

    private JobSeekerProfile getLoggedInJobSeekerProfile() {

        User user = getLoggedInUser();

        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException(
                    "Only job seekers can perform this operation.");
        }

        return jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job seeker profile not found."));
    }

    private Job getJob(Long jobId) {

        return jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found."));
    }
}