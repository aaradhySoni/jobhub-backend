package com.jobhub.service.service;

import com.jobhub.dto.request.JobRequestDTO;
import com.jobhub.dto.response.JobResponseDTO;
import com.jobhub.enums.EmploymentType;
import com.jobhub.enums.ExperienceLevel;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;
import java.util.List;

public interface JobService {

    // Recruiter APIs

    JobResponseDTO createJob(JobRequestDTO request);

    List<JobResponseDTO> getMyJobs();

    JobResponseDTO getMyJob(Long jobId);

    JobResponseDTO updateJob(Long jobId, JobRequestDTO request);

//    void deleteJob(Long jobId);
    void closeJob(Long jobId);

    void publishJob(Long jobId);

    // Public APIs

    //Page<JobResponseDTO> getAllOpenJobs(Pageable pageable);
//         |
    Page<JobResponseDTO> getJobs(
            String title,
            String city,
            EmploymentType employmentType,
            ExperienceLevel experienceLevel,
            Pageable pageable);

    JobResponseDTO getJobById(Long jobId);
}