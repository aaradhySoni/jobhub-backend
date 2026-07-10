package com.jobhub.dto.response;

import com.jobhub.enums.EmploymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedJobResponseDTO {

    private Long savedJobId;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private String city;

    private String state;

    private String country;

    private EmploymentType employmentType;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String currency;

    private LocalDateTime savedAt;
}