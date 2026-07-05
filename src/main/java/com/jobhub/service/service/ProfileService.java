package com.jobhub.service.service;

import com.jobhub.dto.request.JobSeekerProfileRequestDTO;
import com.jobhub.dto.request.RecruiterProfileRequestDTO;
import com.jobhub.dto.response.JobSeekerProfileResponseDTO;
import com.jobhub.dto.response.RecruiterProfileResponseDTO;

public interface ProfileService {

    JobSeekerProfileResponseDTO createJobSeekerProfile(JobSeekerProfileRequestDTO request);

    RecruiterProfileResponseDTO createRecruiterProfile(RecruiterProfileRequestDTO request);

    JobSeekerProfileResponseDTO getMyJobSeekerProfile();

    RecruiterProfileResponseDTO getMyRecruiterProfile();

    JobSeekerProfileResponseDTO updateJobSeekerProfile(JobSeekerProfileRequestDTO request);

    RecruiterProfileResponseDTO updateRecruiterProfile(RecruiterProfileRequestDTO request);
}
