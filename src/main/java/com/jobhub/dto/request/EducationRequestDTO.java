package com.jobhub.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationRequestDTO {

    @NotBlank
    private String institutionName;

    @NotBlank
    private String degree;

    @NotBlank
    private String fieldOfStudy;

    @
            NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double percentage;

    private String description;

    @NotNull
    private Boolean currentlyStudying;
}