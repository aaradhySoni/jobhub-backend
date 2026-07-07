package com.jobhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_seeker_profiles")
public class JobSeekerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String city;

    private String state;

    private String country;

    private String profilePhotoUrl;


    private String linkedinUrl;

    private String githubUrl;

    private String portfolioUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "cover_letter_url")
    private String coverLetterUrl;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
