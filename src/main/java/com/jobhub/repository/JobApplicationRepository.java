package com.jobhub.repository;

import com.jobhub.entity.Job;
import com.jobhub.entity.JobApplication;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    /**
     * Check whether a job seeker has already applied for a job.
     */
    boolean existsByJobAndJobSeekerProfile(Job job, JobSeekerProfile jobSeekerProfile);

    /**
     * Get all applications of a job seeker.
     */
    Page<JobApplication> findByJobSeekerProfile(
            JobSeekerProfile jobSeekerProfile,
            Pageable pageable
    );

    /**
     * Get all applications received for a job.
     */
    Page<JobApplication> findByJob(
            Job job,
            Pageable pageable
    );

    /**
     * Find an application of a job seeker for a specific job.
     */
    Optional<JobApplication> findByJobAndJobSeekerProfile(
            Job job,
            JobSeekerProfile jobSeekerProfile
    );

//    ================= 4 aggregation queries for recruiter dashboard   =================

    long countByJobRecruiterProfile(
            RecruiterProfile recruiterProfile);

    List<JobApplication> findByJobRecruiterProfileOrderByCreatedAtDesc(
            RecruiterProfile recruiterProfile,
            Pageable pageable);
// ================= functions for job Seeker Dashboard
    long countByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);

    long countByJobSeekerProfileAndStatus(JobSeekerProfile jobSeekerProfile, ApplicationStatus status);
}