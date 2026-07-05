package com.jobhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterProfileResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String companyName;

    private String designation;

    private String companyDescription;

    private String companyWebsite;

    private String companyLogoUrl;

    private String city;

    private String state;

    private String country;

    private String linkedinUrl;
}
