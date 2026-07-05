package com.jobhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterProfileRequestDTO {
    @NotBlank
    private String companyName;
    @NotBlank
    private String designation;
    @NotBlank
    private String companyDescription;
    private String companyWebsite;
    private String companyLogoUrl;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    private String linkedinUrl;
}
