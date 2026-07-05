package com.jobhub.mapper;

import com.jobhub.dto.request.SkillRequestDTO;
import com.jobhub.dto.response.SkillResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    // DTO -> Entity
    public Skill toEntity(SkillRequestDTO request,
                          JobSeekerProfile jobSeekerProfile) {

        return Skill.builder()
                .name(request.getName())
                .jobSeekerProfile(jobSeekerProfile)
                .build();
    }

    // Entity -> DTO
    public SkillResponseDTO toResponse(Skill skill) {

        return SkillResponseDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }

    // Update Existing Entity
    public void updateEntity(Skill skill,
                             SkillRequestDTO request) {

        skill.setName(request.getName());
    }
}