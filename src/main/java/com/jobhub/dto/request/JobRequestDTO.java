package com.jobhub.dto.request;

import com.jobhub.enums.EmploymentType;
import com.jobhub.enums.ExperienceLevel;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String requirements;

    @NotBlank
    private String responsibilities;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotNull
    private EmploymentType employmentType;

    @NotNull
    private ExperienceLevel experienceLevel;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal salaryMin;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal salaryMax;

    @NotBlank
    private String currency;

    @NotNull
    @Min(1)
    private Integer vacancies;

    @NotNull
    private LocalDate applicationDeadline;
}