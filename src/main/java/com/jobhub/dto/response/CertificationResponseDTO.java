package com.jobhub.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationResponseDTO {

    private Long id;

    private String name;

    private String issuingOrganization;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String certificateFileUrl;
}