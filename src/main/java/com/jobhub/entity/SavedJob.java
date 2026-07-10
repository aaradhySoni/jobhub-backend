package com.jobhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "saved_jobs",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_saved_job",
                        columnNames = {"job_id", "job_seeker_profile_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= Job =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // ================= Job Seeker =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_profile_id", nullable = false)
    private JobSeekerProfile jobSeekerProfile;

    // ================= Audit =================

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    // There is nothing to update in SavedJob either exists as a savedJob or not
    }
}