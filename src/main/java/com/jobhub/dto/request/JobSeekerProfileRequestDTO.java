package com.jobhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeekerProfileRequestDTO {
    @NotBlank
    private String headline;

    @Size(max = 500)
    private String bio;

    private String city;
    @NotBlank

    private String state;
    @NotBlank

    private String country;

    private String profilePhotoUrl;
    @NotBlank
    private String resumeUrl;

    private String linkedinUrl;

    private String githubUrl;

    private String portfolioUrl;
}
