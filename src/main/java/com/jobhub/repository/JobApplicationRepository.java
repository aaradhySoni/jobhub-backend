package com.jobhub.repository;

import com.jobhub.entity.Job;
import com.jobhub.entity.JobApplication;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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

}