package com.jobhub.dto.response;

import com.jobhub.enums.EmploymentType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceResponseDTO {

    private Long id;

    private String jobTitle;

    private String companyName;

    private EmploymentType employmentType;

    private String location;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean currentlyWorking;

    private String description;
}