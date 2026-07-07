package com.jobhub.repository;

import com.jobhub.entity.Education;
import com.jobhub.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);

}
