package com.jobhub.service.impl;

import com.jobhub.dto.response.JobSeekerDashboardResponseDTO;
import com.jobhub.dto.response.RecentApplicationResponseDTO;
import com.jobhub.dto.response.RecruiterDashboardResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.ApplicationStatus;
import com.jobhub.enums.JobStatus;
import com.jobhub.enums.Role;
import com.jobhub.mapper.DashboardMapper;
import com.jobhub.repository.*;
import com.jobhub.service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final DashboardMapper dashboardMapper;

    private final SavedJobRepository savedJobRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    @Override
    public RecruiterDashboardResponseDTO getRecruiterDashboard() {

        RecruiterProfile recruiter = getLoggedInRecruiter();

        return RecruiterDashboardResponseDTO.builder()
                .companyName(recruiter.getCompanyName())
                .totalJobs(jobRepository.countByRecruiterProfile(recruiter))
                .openJobs(jobRepository.countByRecruiterProfileAndStatus(
                        recruiter,
                        JobStatus.OPEN))
                .closedJobs(jobRepository.countByRecruiterProfileAndStatus(
                        recruiter,
                        JobStatus.CLOSED))
                .totalApplications(
                        jobApplicationRepository.countByJobRecruiterProfile(
                                recruiter))
                .build();
    }

    @Override
    public List<RecentApplicationResponseDTO> getRecentApplications() {

        RecruiterProfile recruiter = getLoggedInRecruiter();

        return jobApplicationRepository
                .findByJobRecruiterProfileOrderByCreatedAtDesc(
                        recruiter,
                        PageRequest.of(0,5))
                .stream()
                .map(dashboardMapper::toRecentApplicationResponseDTO)
                .toList();
    }

    @Override
    public JobSeekerDashboardResponseDTO getJobSeekerDashboard() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return JobSeekerDashboardResponseDTO.builder()
                .totalApplications(
                        jobApplicationRepository.countByJobSeekerProfile(profile))

                .applied(
                        jobApplicationRepository.countByJobSeekerProfileAndStatus(
                                profile,
                                ApplicationStatus.APPLIED))

                .underReview(
                        jobApplicationRepository.countByJobSeekerProfileAndStatus(
                                profile,
                                ApplicationStatus.UNDER_REVIEW))

                .shortlisted(
                        jobApplicationRepository.countByJobSeekerProfileAndStatus(
                                profile,
                                ApplicationStatus.SHORTLISTED))

                .rejected(
                        jobApplicationRepository.countByJobSeekerProfileAndStatus(
                                profile,
                                ApplicationStatus.REJECTED))

                .hired(
                        jobApplicationRepository.countByJobSeekerProfileAndStatus(
                                profile,
                                ApplicationStatus.HIRED))

                .savedJobs(
                        savedJobRepository.countByJobSeekerProfile(profile))

                .build();
    }

    //================ Helper Methods =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));
    }

    private RecruiterProfile getLoggedInRecruiter() {

        User user = getLoggedInUser();

        if(user.getRole()!= Role.RECRUITER){

            throw new RuntimeException(
                    "Only recruiters can access dashboard.");
        }

        return recruiterProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Recruiter profile not found."));
    }

    private JobSeekerProfile getLoggedInJobSeekerProfile() {

        User user = getLoggedInUser();

        if (user.getRole() != Role.JOB_SEEKER) {

            throw new RuntimeException(
                    "Only job seekers can access dashboard.");
        }

        return jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job seeker profile not found."));
    }
}