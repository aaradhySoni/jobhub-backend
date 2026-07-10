package com.jobhub.mapper;

import com.jobhub.dto.response.RecentApplicationResponseDTO;
import com.jobhub.entity.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class DashboardMapper {

    public RecentApplicationResponseDTO toRecentApplicationResponseDTO(
            JobApplication application) {

        return RecentApplicationResponseDTO.builder()
                .applicationId(application.getId())
                .applicantName(application.getJobSeekerProfile()
                        .getUser()
                        .getName())
                .jobTitle(application.getJob()
                        .getTitle())
                .status(application.getStatus())
                .appliedAt(application.getCreatedAt())
                .build();
    }
}