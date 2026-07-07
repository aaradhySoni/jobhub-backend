package com.jobhub.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String technologiesUsed;

    private String projectUrl;

    private String githubUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean currentlyWorking;
}