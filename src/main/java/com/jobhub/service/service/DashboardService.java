package com.jobhub.service.service;

import com.jobhub.dto.response.JobSeekerDashboardResponseDTO;
import com.jobhub.dto.response.RecentApplicationResponseDTO;
import com.jobhub.dto.response.RecruiterDashboardResponseDTO;

import java.util.List;

public interface DashboardService {

    RecruiterDashboardResponseDTO getRecruiterDashboard();

    List<RecentApplicationResponseDTO> getRecentApplications();

//    jobSeeker
    JobSeekerDashboardResponseDTO getJobSeekerDashboard();
}