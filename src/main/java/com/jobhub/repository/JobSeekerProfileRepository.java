package com.jobhub.repository;

import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerProfileRepository
        extends JpaRepository<JobSeekerProfile, Long> {
    Optional<JobSeekerProfile> findByUser(User user);
}
