package com.jobhub.dto.response;

import com.jobhub.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantResponseDTO {

    private Long applicationId;

    private Long applicantId;

    private String applicantName;

    private String headline;

    private String city;

    private String state;

    private String country;

    private String resumeUrl;

    private String coverLetterUrl;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}