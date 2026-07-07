package com.jobhub.repository;

import com.jobhub.entity.Certification;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    List<Certification> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);
}