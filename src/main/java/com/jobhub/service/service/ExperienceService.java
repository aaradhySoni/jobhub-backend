package com.jobhub.service.service;

import com.jobhub.dto.request.ExperienceRequestDTO;
import com.jobhub.dto.response.ExperienceResponseDTO;
import java.util.List;

//add , update , delete ,see experiences , it contains 4 methods.
public interface ExperienceService {

    ExperienceResponseDTO addExperience(ExperienceRequestDTO request);

    List<ExperienceResponseDTO> getMyExperiences();

    ExperienceResponseDTO updateExperience(Long experienceId,
                                           ExperienceRequestDTO request);

    void deleteExperience(Long experienceId);
}


