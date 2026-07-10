package com.jobhub.repository;

import com.jobhub.entity.Job;
import com.jobhub.entity.JobApplication;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.enums.EmploymentType;
import com.jobhub.enums.ExperienceLevel;
import com.jobhub.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiterProfile(RecruiterProfile recruiterProfile);

    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    //FILTERING METHODS
    Page<Job> findByStatusAndTitleContainingIgnoreCase(
            JobStatus status,
            String title,
            Pageable pageable);

    Page<Job> findByStatusAndCityIgnoreCase(
            JobStatus status,
            String city,
            Pageable pageable);

    Page<Job> findByStatusAndEmploymentType(
            JobStatus status,
            EmploymentType employmentType,
            Pageable pageable);

    Page<Job> findByStatusAndExperienceLevel(
            JobStatus status,
            ExperienceLevel experienceLevel,
            Pageable pageable);

//    ================= 4 aggregation queries for recruiter dashboard   =================



    long countByRecruiterProfile(
            RecruiterProfile recruiterProfile);

    long countByRecruiterProfileAndStatus(
            RecruiterProfile recruiterProfile,
            JobStatus status);



}