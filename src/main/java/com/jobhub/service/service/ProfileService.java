package com.jobhub.service.service;

import com.jobhub.dto.request.JobSeekerProfileRequestDTO;
import com.jobhub.dto.request.RecruiterProfileRequestDTO;
import com.jobhub.dto.response.JobSeekerProfileResponseDTO;
import com.jobhub.dto.response.RecruiterProfileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    JobSeekerProfileResponseDTO createJobSeekerProfile(JobSeekerProfileRequestDTO request);

    RecruiterProfileResponseDTO createRecruiterProfile(RecruiterProfileRequestDTO request);

    JobSeekerProfileResponseDTO getMyJobSeekerProfile();

    RecruiterProfileResponseDTO getMyRecruiterProfile();

    JobSeekerProfileResponseDTO updateJobSeekerProfile(JobSeekerProfileRequestDTO request);

    RecruiterProfileResponseDTO updateRecruiterProfile(RecruiterProfileRequestDTO request);

    String uploadResume(MultipartFile file);

    String uploadCoverLetter(MultipartFile file);

    void deleteResume();

    void deleteCoverLetter();
}
