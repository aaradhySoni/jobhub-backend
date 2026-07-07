package com.jobhub.dto.request;

import com.jobhub.enums.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceRequestDTO {

    @NotBlank
    private String jobTitle;

    @NotBlank
    private String companyName;

    @NotNull
    private EmploymentType employmentType;

    @NotBlank
    private String location;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Boolean currentlyWorking;

    private String description;
}