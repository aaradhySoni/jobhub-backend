package com.jobhub.service.impl;

import com.jobhub.dto.response.ApplicantResponseDTO;
import com.jobhub.dto.response.JobApplicationResponseDTO;
import com.jobhub.entity.Job;
import com.jobhub.entity.JobApplication;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.ApplicationStatus;
import com.jobhub.enums.JobStatus;
import com.jobhub.enums.Role;
import com.jobhub.mapper.JobApplicationMapper;
import com.jobhub.repository.JobApplicationRepository;
import com.jobhub.repository.JobRepository;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;
    private final JobApplicationMapper jobApplicationMapper;


    @Override
    public void applyToJob(Long jobId) {

        JobSeekerProfile profile =
                getLoggedInJobSeekerProfile();

        Job job = getJob(jobId);

        validateJob(job);

        boolean alreadyApplied =
                jobApplicationRepository
                        .existsByJobAndJobSeekerProfile(
                                job,
                                profile);

        if (alreadyApplied) {

            throw new RuntimeException(
                    "You have already applied for this job.");
        }

        JobApplication application =
                JobApplication.builder()
                        .job(job)
                        .jobSeekerProfile(profile)
                        .status(ApplicationStatus.APPLIED)
                        .build();

        jobApplicationRepository.save(application);
    }

    @Override
    public Page<JobApplicationResponseDTO> getMyApplications(
            Pageable pageable) {

        JobSeekerProfile profile =
                getLoggedInJobSeekerProfile();

        Page<JobApplication> applications =
                jobApplicationRepository.findByJobSeekerProfile(
                        profile,
                        pageable);

        return applications.map(
                jobApplicationMapper::toResponseDTO);
    }

    @Override
    public Page<ApplicantResponseDTO> getApplicantsForJob(
            Long jobId,
            Pageable pageable) {

        User user = getLoggedInUser();

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can view applicants.");
        }

        Job job = getJob(jobId);

        if (!job.getRecruiterProfile()
                .getUser()
                .getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not allowed to view applicants for this job.");
        }

        Page<JobApplication> applications =
                jobApplicationRepository.findByJob(
                        job,
                        pageable);

        return applications.map(
                jobApplicationMapper::toApplicantResponseDTO);
    }

    @Override
    public void updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status) {

        User user = getLoggedInUser();

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can update application status.");
        }

        JobApplication application =
                jobApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found."));

        if (!application.getJob()
                .getRecruiterProfile()
                .getUser()
                .getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this application.");
        }

        application.setStatus(status);

        jobApplicationRepository.save(application);
    }

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

    private void validateJob(Job job) {

        if (job.getStatus() != JobStatus.OPEN) {

            throw new RuntimeException(
                    "This job is no longer accepting applications.");
        }

        if (job.getApplicationDeadline()
                .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Application deadline has passed.");
        }
    }

}