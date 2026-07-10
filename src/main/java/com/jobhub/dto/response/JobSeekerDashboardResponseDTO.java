package com.jobhub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeekerDashboardResponseDTO {

    private Long totalApplications;

    private Long applied;

    private Long underReview;

    private Long shortlisted;

    private Long rejected;

    private Long hired;

    private Long savedJobs;
}