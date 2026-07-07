package com.jobhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String issuingOrganization;

    @NotNull
    private LocalDate issueDate;

    private LocalDate expiryDate;
}