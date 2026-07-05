package com.jobhub.mapper;

import com.jobhub.dto.request.JobSeekerProfileRequestDTO;
import com.jobhub.dto.response.JobSeekerProfileResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.User;
import org.springframework.stereotype.Component;

@Component
public class JobSeekerProfileMapper {
    //This method creates an Entity from a DTO.
    public JobSeekerProfile toEntity(JobSeekerProfileRequestDTO request, User user) {

        return JobSeekerProfile.builder()
                .user(user)
                .headline(request.getHeadline())
                .bio(request.getBio())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .profilePhotoUrl(request.getProfilePhotoUrl())
                .resumeUrl(request.getResumeUrl())
                .linkedinUrl(request.getLinkedinUrl())
                .githubUrl(request.getGithubUrl())
                .portfolioUrl(request.getPortfolioUrl())
                .build();
    }

    public JobSeekerProfileResponseDTO toResponse(JobSeekerProfile profile) {

        return JobSeekerProfileResponseDTO.builder()
                .id(profile.getId())
                .name(profile.getUser().getName())
                .email(profile.getUser().getEmail())
                .phone(profile.getUser().getPhone())
                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .profilePhotoUrl(profile.getProfilePhotoUrl())
                .resumeUrl(profile.getResumeUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .githubUrl(profile.getGithubUrl())
                .portfolioUrl(profile.getPortfolioUrl())
                .build();
    }


//This mapper will have two methods:
//DTO → Entity
//Entity → DTO

    public void updateEntity(
            JobSeekerProfile profile,
            JobSeekerProfileRequestDTO request) {

        profile.setHeadline(request.getHeadline());
        profile.setBio(request.getBio());
        profile.setCity(request.getCity());
        profile.setState(request.getState());
        profile.setCountry(request.getCountry());
        profile.setProfilePhotoUrl(request.getProfilePhotoUrl());
        profile.setResumeUrl(request.getResumeUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());
    }
}