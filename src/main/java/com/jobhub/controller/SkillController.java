package com.jobhub.controller;

import com.jobhub.dto.request.SkillRequestDTO;
import com.jobhub.dto.response.SkillResponseDTO;
import com.jobhub.service.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponseDTO> addSkill(
            @Valid @RequestBody SkillRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skillService.addSkill(request));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getMySkills() {

        return ResponseEntity.ok(skillService.getMySkills());
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillResponseDTO> updateSkill(
            @PathVariable Long skillId,
            @Valid @RequestBody SkillRequestDTO request) {

        return ResponseEntity.ok(
                skillService.updateSkill(skillId, request));
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<String> deleteSkill(
            @PathVariable Long skillId) {

        skillService.deleteSkill(skillId);

        return ResponseEntity.ok("Skill deleted successfully.");
    }
}