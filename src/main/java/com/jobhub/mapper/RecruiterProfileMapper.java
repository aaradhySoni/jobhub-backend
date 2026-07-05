package com.jobhub.mapper;

import com.jobhub.dto.request.RecruiterProfileRequestDTO;
import com.jobhub.dto.response.RecruiterProfileResponseDTO;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class RecruiterProfileMapper {
    public RecruiterProfile toEntity(
            RecruiterProfileRequestDTO request,
            User user){
        return RecruiterProfile.builder()
                .user(user)
                .companyName(request.getCompanyName())
                .designation(request.getDesignation())
                .companyDescription(request.getCompanyDescription())
                .companyWebsite(request.getCompanyWebsite())
                .companyLogoUrl(request.getCompanyLogoUrl())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .linkedinUrl(request.getLinkedinUrl())
                .build();
    }
    public RecruiterProfileResponseDTO toResponse(
            RecruiterProfile profile){
        return RecruiterProfileResponseDTO.builder()
                .id(profile.getId())
                .name(profile.getUser().getName())
                .email(profile.getUser().getEmail())
                .phone(profile.getUser().getPhone())
                .companyName(profile.getCompanyName())
                .designation(profile.getDesignation())
                .companyDescription(profile.getCompanyDescription())
                .companyWebsite(profile.getCompanyWebsite())
                .companyLogoUrl(profile.getCompanyLogoUrl())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .linkedinUrl(profile.getLinkedinUrl())
                .build();
    }
    public void updateEntity(
            RecruiterProfile profile,
            RecruiterProfileRequestDTO request) {

        profile.setCompanyName(request.getCompanyName());
        profile.setDesignation(request.getDesignation());
        profile.setCompanyDescription(request.getCompanyDescription());
        profile.setCompanyWebsite(request.getCompanyWebsite());
        profile.setCompanyLogoUrl(request.getCompanyLogoUrl());
        profile.setCity(request.getCity());
        profile.setState(request.getState());
        profile.setCountry(request.getCountry());
        profile.setLinkedinUrl(request.getLinkedinUrl());
    }

}
