package com.jobhub.service.impl;

import com.jobhub.dto.request.SkillRequestDTO;
import com.jobhub.dto.response.SkillResponseDTO;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.Skill;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.mapper.SkillMapper;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.SkillRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    //DI
    private final SkillRepository skillRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;
    private final SkillMapper skillMapper;

    //helper method
    private User getLoggedInUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
//    helper method -> Instead of repeatedly writing: Get logged-in user Check role Find profile
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

    @Override
    public SkillResponseDTO addSkill(SkillRequestDTO request) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile(); //get profile of jobseeker
        Skill skill = skillMapper.toEntity(request, profile);     //
        Skill savedSkill = skillRepository.save(skill);
        return skillMapper.toResponse(savedSkill);
    }

    @Override
    public List<SkillResponseDTO> getMySkills() {
        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return skillRepository.findByJobSeekerProfile(profile)
                .stream()    //Converts the list into a Stream so we can process each element one by one
                .map(skillMapper::toResponse)
                .toList();
    }

    @Override
    public SkillResponseDTO updateSkill(Long skillId, SkillRequestDTO request) {
        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found."));
        //ownership check
        if (!skill.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this skill.");
        }
            skillMapper.updateEntity(skill, request);

            Skill updatedSkill = skillRepository.save(skill);

            return skillMapper.toResponse(updatedSkill);
        }


    @Override
    public void deleteSkill(Long skillId) {
        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found."));
        if (!skill.getJobSeekerProfile().getId()
                .equals(profile.getId())) {
            throw new RuntimeException(
                    "You are not allowed to delete this skill.");
        }
        skillRepository.delete(skill);

    }
}
