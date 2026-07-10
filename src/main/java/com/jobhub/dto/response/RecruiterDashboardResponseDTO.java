package com.jobhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterDashboardResponseDTO {

    private String companyName;

    private Long totalJobs;

    private Long openJobs;

    private Long closedJobs;

    private Long totalApplications;
}