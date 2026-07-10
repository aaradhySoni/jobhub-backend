package com.jobhub.repository;

import com.jobhub.entity.Job;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.SavedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
//    Prevent duplicate saves , if job is already saved then cannot be saved again
    boolean existsByJobAndJobSeekerProfile(Job job, JobSeekerProfile jobSeekerProfile);

//    My Saved Jobs
    Page<SavedJob> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile, Pageable pageable);

//    Check if a job is saved
    Optional<SavedJob> findByJobAndJobSeekerProfile(Job job, JobSeekerProfile jobSeekerProfile);
//    Remove saved job
    void deleteByJobAndJobSeekerProfile(Job job, JobSeekerProfile jobSeekerProfile);


//    ============= function for job seeker dashboard
    long countByJobSeekerProfile(JobSeekerProfile profile);
}