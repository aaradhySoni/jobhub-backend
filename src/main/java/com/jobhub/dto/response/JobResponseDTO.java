package com.jobhub.dto.response;

import com.jobhub.enums.EmploymentType;
import com.jobhub.enums.ExperienceLevel;
import com.jobhub.enums.JobStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String responsibilities;

    private String city;

    private String state;

    private String country;

    private EmploymentType employmentType;

    private ExperienceLevel experienceLevel;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String currency;

    private Integer vacancies;

    private LocalDate applicationDeadline;

    private JobStatus status;

    // Recruiter Details

    private String recruiterName;

    private String companyName;

    // Audit

    private LocalDateTime postedAt;

    //logo

    private String companyLogoUrl;
}