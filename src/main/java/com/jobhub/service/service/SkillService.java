package com.jobhub.service.service;

import com.jobhub.dto.request.SkillRequestDTO;
import com.jobhub.dto.response.SkillResponseDTO;

import java.util.List;

public interface SkillService {

    SkillResponseDTO addSkill(SkillRequestDTO request);

    List<SkillResponseDTO> getMySkills();

    SkillResponseDTO updateSkill(Long skillId, SkillRequestDTO request);

    void deleteSkill(Long skillId);
}
