package com.jobhub.repository;

import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);
}