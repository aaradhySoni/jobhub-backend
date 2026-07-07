package com.jobhub.controller;

import com.jobhub.dto.request.ProjectRequestDTO;
import com.jobhub.dto.response.ProjectResponseDTO;
import com.jobhub.service.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> addProject(
            @Valid @RequestBody ProjectRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.addProject(request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getMyProjects() {

        return ResponseEntity.ok(
                projectService.getMyProjects());
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequestDTO request) {

        return ResponseEntity.ok(
                projectService.updateProject(projectId, request));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(
            @PathVariable Long projectId) {

        projectService.deleteProject(projectId);

        return ResponseEntity.ok("Project deleted successfully.");
    }
}