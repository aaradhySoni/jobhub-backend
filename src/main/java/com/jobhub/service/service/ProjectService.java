package com.jobhub.service.service;

import com.jobhub.dto.request.ProjectRequestDTO;
import com.jobhub.dto.response.ProjectResponseDTO;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO addProject(ProjectRequestDTO request);

    List<ProjectResponseDTO> getMyProjects();

    ProjectResponseDTO updateProject(Long projectId,
                                     ProjectRequestDTO request);

    void deleteProject(Long projectId);
}