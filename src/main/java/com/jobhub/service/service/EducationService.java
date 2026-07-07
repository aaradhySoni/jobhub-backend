package com.jobhub.service.service;

import com.jobhub.dto.request.EducationRequestDTO;
import com.jobhub.dto.response.EducationResponseDTO;

import java.util.List;

public interface EducationService {

    EducationResponseDTO addEducation(EducationRequestDTO request);

    List<EducationResponseDTO> getMyEducations();

    EducationResponseDTO updateEducation(Long educationId,
                                         EducationRequestDTO request);

    void deleteEducation(Long educationId);
}