package com.jobhub.repository;

import com.jobhub.entity.Experience;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository
        extends JpaRepository<Experience, Long> {

    List<Experience> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);

}