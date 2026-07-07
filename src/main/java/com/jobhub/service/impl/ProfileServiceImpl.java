package com.jobhub.service.impl;

import com.jobhub.dto.request.JobSeekerProfileRequestDTO;
import com.jobhub.dto.request.RecruiterProfileRequestDTO;
import com.jobhub.dto.response.JobSeekerProfileResponseDTO;
import com.jobhub.dto.response.RecruiterProfileResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.mapper.JobSeekerProfileMapper;
import com.jobhub.mapper.RecruiterProfileMapper;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.RecruiterProfileRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileMapper jobSeekerProfileMapper;
    private final RecruiterProfileMapper recruiterProfileMapper;



//helper method hai ,  we'll need the "logged-in user" in many future modules:Profile, Job Application ,Company This follows the DRY (Don't Repeat Yourself) principle and makes the code cleaner and easier to maintain.
    private User getLoggedInUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @Override
    public JobSeekerProfileResponseDTO createJobSeekerProfile(JobSeekerProfileRequestDTO request) {
//Step 1: Get the Logged-in User
        User user = getLoggedInUser();
//Step 2: Validate User Role
        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException("Only job seekers can create a job seeker profile.");
        }
//Step 3: Check Duplicate Profile/profile exist or not
        if (jobSeekerProfileRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Profile already exists.");
        }
//Step 4: DTO → Entity Mapping
        JobSeekerProfile profile =
                jobSeekerProfileMapper.toEntity(request, user);
//        Step 5: Save Entity
        JobSeekerProfile savedProfile =
                jobSeekerProfileRepository.save(profile);
//Step 6: Entity → Response DTO
        return jobSeekerProfileMapper.toResponse(savedProfile);
    }

    @Override
    public RecruiterProfileResponseDTO createRecruiterProfile(RecruiterProfileRequestDTO request) {

        User user = getLoggedInUser();

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can create a recruiter profile.");
        }

        if (recruiterProfileRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Profile already exists.");
        }


        RecruiterProfile profile = recruiterProfileMapper.toEntity(request, user);

        RecruiterProfile savedProfile = recruiterProfileRepository.save(profile);

        return recruiterProfileMapper.toResponse(savedProfile);
    }

    @Override
    public JobSeekerProfileResponseDTO getMyJobSeekerProfile() {

        User user = getLoggedInUser();

        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException(
                    "Only job seekers can access this profile.");
        }
        JobSeekerProfile profile = jobSeekerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found."));
        return jobSeekerProfileMapper.toResponse(profile);
    }

    @Override
    public RecruiterProfileResponseDTO getMyRecruiterProfile() {

        User user = getLoggedInUser();
        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can access this profile.");
        }
        RecruiterProfile profile = recruiterProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found."));
        return recruiterProfileMapper.toResponse(profile);
    }

    @Override
    public JobSeekerProfileResponseDTO updateJobSeekerProfile(JobSeekerProfileRequestDTO request) {
//        Get Logged-in User
        User user = getLoggedInUser();
//        Validate Role
        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException(
                    "Only job seekers can update their profile.");
        }
//        Find Existing Profile
        JobSeekerProfile profile = jobSeekerProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found."));
//        Mapper.updateEntity() | update existing profile
        jobSeekerProfileMapper.updateEntity(profile, request);
//        Repository.save()
        JobSeekerProfile updatedProfile =
                jobSeekerProfileRepository.save(profile);
//return updated profile
        return jobSeekerProfileMapper.toResponse(updatedProfile);
    }

    @Override
    public RecruiterProfileResponseDTO updateRecruiterProfile(RecruiterProfileRequestDTO request) {

        User user = getLoggedInUser();

        if (user.getRole() != Role.RECRUITER) {
            throw new RuntimeException(
                    "Only recruiters can update their profile.");
        }

        RecruiterProfile profile = recruiterProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found."));

        recruiterProfileMapper.updateEntity(profile, request);

        RecruiterProfile updatedProfile =
                recruiterProfileRepository.save(profile);

        return recruiterProfileMapper.toResponse(updatedProfile);
    }



//resume and CV
@Override
public String uploadResume(MultipartFile file) {

    return uploadDocument(
            file,
            "uploads/resumes",
            "resume"
    );
}
@Override
public String uploadCoverLetter(MultipartFile file) {

    return uploadDocument(
            file,
            "uploads/cover-letters",
            "coverLetter"
    );
}
    @Override
    public void deleteResume() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        deleteFile(profile.getResumeUrl());

        profile.setResumeUrl(null);

        jobSeekerProfileRepository.save(profile);
    }
    @Override
    public void deleteCoverLetter() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        deleteFile(profile.getCoverLetterUrl());

        profile.setCoverLetterUrl(null);

        jobSeekerProfileRepository.save(profile);
    }
//helper method for resume /CV
private String uploadDocument(
        MultipartFile file,
        String uploadDirectory,
        String documentType) {

    if (file.isEmpty()) {
        throw new RuntimeException(
                "Please upload a PDF file.");
    }

    if (!"application/pdf".equals(file.getContentType())) {
        throw new RuntimeException(
                "Only PDF files are allowed.");
    }

    if (file.getSize() > 5 * 1024 * 1024) {
        throw new RuntimeException(
                "Maximum allowed file size is 5 MB.");
    }

    JobSeekerProfile profile =
            getLoggedInJobSeekerProfile();


    // Resume already exists
    if ("resume".equals(documentType)
            && profile.getResumeUrl() != null
            && !profile.getResumeUrl().isBlank()) {

        throw new RuntimeException(
                "Resume already exists. Please delete the existing resume before uploading a new one.");
    }

// Cover Letter already exists
    if ("coverLetter".equals(documentType)
            && profile.getCoverLetterUrl() != null
            && !profile.getCoverLetterUrl().isBlank()) {

        throw new RuntimeException(
                "Cover Letter already exists. Please delete the existing cover letter before uploading a new one.");
    }

    try {

        Path uploadPath =
                Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {

            Files.createDirectories(uploadPath);
        }

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path filePath =
                uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        String relativePath =
                uploadDirectory.replace("\\", "/")
                        + "/"
                        + fileName;

        if ("resume".equals(documentType)) {

            profile.setResumeUrl(relativePath);

        } else {

            profile.setCoverLetterUrl(relativePath);
        }

        jobSeekerProfileRepository.save(profile);

        return documentType.equals("resume")
                ? "Resume uploaded successfully."
                : "Cover Letter uploaded successfully.";

    } catch (Exception e) {

        throw new RuntimeException(
                "Failed to upload document.", e);
    }
}

    private JobSeekerProfile getLoggedInJobSeekerProfile() {

        User user = getLoggedInUser();

        if (user.getRole() != Role.JOB_SEEKER) {
            throw new RuntimeException(
                    "Only job seekers can perform this operation.");
        }

        return jobSeekerProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Job seeker profile not found."));
    }
    private void deleteFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return;
        }
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);

        }catch (Exception e) {
            throw new RuntimeException(
                    "Failed to delete file.",
                    e);
        }
    }
}