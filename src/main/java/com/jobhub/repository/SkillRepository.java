package com.jobhub.repository;

import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);

}
