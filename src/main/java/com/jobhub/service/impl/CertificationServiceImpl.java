package com.jobhub.service.impl;

import com.jobhub.dto.request.CertificationRequestDTO;
import com.jobhub.dto.response.CertificationResponseDTO;
import com.jobhub.entity.Certification;
import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.Role;
import com.jobhub.mapper.CertificationMapper;
import com.jobhub.repository.CertificationRepository;
import com.jobhub.repository.JobSeekerProfileRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.service.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//These classes are Java's built-in NIO (New I/O) API.     They're the production standard for working with files.
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;

    @Override
    public CertificationResponseDTO addCertification(CertificationRequestDTO request) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Certification certification =
                certificationMapper.toEntity(request, profile);

        Certification savedCertification =
                certificationRepository.save(certification);

        return certificationMapper.toResponse(savedCertification);
    }

    @Override
    public List<CertificationResponseDTO> getMyCertifications() {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        return certificationRepository.findByJobSeekerProfile(profile)
                .stream()
                .map(certificationMapper::toResponse)
                .toList();
    }

    @Override
    public CertificationResponseDTO updateCertification(Long certificationId, CertificationRequestDTO request) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Certification not found."));

        if (!certification.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this certification.");
        }

        certificationMapper.updateEntity(certification, request);

        Certification updatedCertification =
                certificationRepository.save(certification);

        return certificationMapper.toResponse(updatedCertification);
    }

    @Override
    public void deleteCertification(Long certificationId) {

        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new RuntimeException("Certification not found."));

        if (!certification.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this certification.");
        }

        certificationRepository.delete(certification);
    }

    @Override
    public String uploadCertificate(Long certificationId, MultipartFile file) {

        // 1. Check if file is empty
        if (file.isEmpty()) {
            throw new RuntimeException("Please upload a PDF file.");
        }

        // 2. Allow only PDF
        if (!"application/pdf".equals(file.getContentType())) {
            throw new RuntimeException("Only PDF files are allowed.");
        }

        // 3. Maximum size = 5 MB
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Maximum allowed file size is 5 MB.");
        }

        // 4. Get logged-in user's profile
        JobSeekerProfile profile = getLoggedInJobSeekerProfile();

        // 5. Find certification
        Certification certification = certificationRepository
                .findById(certificationId)
                .orElseThrow(() ->
                        new RuntimeException("Certification not found."));

        // 6. Ownership check
        if (!certification.getJobSeekerProfile().getId()
                .equals(profile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to upload a certificate for this record.");
        }

        try {

            // 7. Upload folder
            String uploadDir = "uploads/certificates";

            Path uploadPath = Paths.get(uploadDir);

            // 8. Create folder if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 9. Generate unique filename
            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            // 10. Full file path
            Path filePath = uploadPath.resolve(fileName);

            // 11. Copy file
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // 12. Save file path in database
            certification.setCertificateFileUrl(filePath.toString());

            certificationRepository.save(certification);

            return "Certificate uploaded successfully.";

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to upload certificate.", e);
        }
    }

    // ================= Helper Methods =================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
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
}