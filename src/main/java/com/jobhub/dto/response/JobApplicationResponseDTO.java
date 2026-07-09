package com.jobhub.dto.response;

import com.jobhub.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationResponseDTO {

    private Long applicationId;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String city;

    private String state;

    private String country;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}