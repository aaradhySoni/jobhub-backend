package com.jobhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeekerProfileResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String headline;

    private String bio;

    private String city;

    private String state;

    private String country;

    private String profilePhotoUrl;

    private String resumeUrl;

    private String linkedinUrl;

    private String githubUrl;

    private String portfolioUrl;
}
