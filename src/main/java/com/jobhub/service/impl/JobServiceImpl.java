package com.jobhub.service.impl;

import com.jobhub.dto.request.JobRequestDTO;
import com.jobhub.dto.response.JobResponseDTO;
import com.jobhub.entity.Job;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;
import com.jobhub.enums.EmploymentType;
import com.jobhub.enums.ExperienceLevel;
import com.jobhub.enums.JobStatus;
import com.jobhub.enums.Role;
import com.jobhub.mapper.JobMapper;
import com.jobhub.repository.JobRepository;
import com.jobhub.repository.RecruiterProfileRepository;
import com.jobhub.repository.UserRepository;
import com.jobhub.security.CurrentUserService;
import com.jobhub.service.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final CurrentUserService currentUserService;

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;
    @Override
    public JobResponseDTO createJob(JobRequestDTO request) {

        RecruiterProfile recruiterProfile =
                currentUserService.getCurrentRecruiter();

        Job job =
                jobMapper.toEntity(request, recruiterProfile);

        Job savedJob =
                jobRepository.save(job);

        return jobMapper.toResponse(savedJob);
    }
    @Override
    public List<JobResponseDTO> getMyJobs() {

        RecruiterProfile recruiterProfile =
                currentUserService.getCurrentRecruiter();

        return jobRepository.findByRecruiterProfile(recruiterProfile)
                .stream()
                .map(jobMapper::toResponse)
                .toList();
    }
    @Override
    public JobResponseDTO getMyJob(Long jobId) {

        RecruiterProfile recruiterProfile =
                currentUserService.getCurrentRecruiter();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found."));

        if (!job.getRecruiterProfile().getId()
                .equals(recruiterProfile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to view this job.");
        }

        return jobMapper.toResponse(job);
    }
    @Override
    public JobResponseDTO updateJob(
            Long jobId,
            JobRequestDTO request) {

        RecruiterProfile recruiterProfile =
                currentUserService.getCurrentRecruiter();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found."));

        if (!job.getRecruiterProfile().getId()
                .equals(recruiterProfile.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this job.");
        }

        jobMapper.updateEntity(job, request);

        Job updatedJob =
                jobRepository.save(job);

        return jobMapper.toResponse(updatedJob);
    }

    @Override
    public void closeJob(Long jobId) {

        Job job = getRecruiterJob(jobId);

        if (job.getStatus() == JobStatus.CLOSED) {
            throw new RuntimeException("Job is already closed.");
        }

        job.setStatus(JobStatus.CLOSED);

        jobRepository.save(job);
    }


    @Override
    public void publishJob(Long jobId) {

        Job job = getRecruiterJob(jobId);

        if (job.getStatus() == JobStatus.OPEN) {
            throw new RuntimeException("Job is already published.");
        }

        job.setStatus(JobStatus.OPEN);

        jobRepository.save(job);
    }

//    @Override
//    public void deleteJob(Long jobId) {
//
//        RecruiterProfile recruiterProfile =
//                currentUserService.getCurrentRecruiter();
//
//        Job job = jobRepository.findById(jobId)
//                .orElseThrow(() ->
//                        new RuntimeException("Job not found."));
//
//        if (!job.getRecruiterProfile().getId()
//                .equals(recruiterProfile.getId())) {
//
//            throw new RuntimeException(
//                    "You are not allowed to delete this job.");
//        }
//
//        job.setStatus(JobStatus.CLOSED);
//
//        jobRepository.save(job);
//    }

//   ============ Public APIs ==============
//@Override
//public Page<JobResponseDTO> getAllOpenJobs(Pageable pageable) {
//
//    Page<Job> jobs = jobRepository.findByStatus(
//            JobStatus.OPEN,
//            pageable);
//
//    return jobs.map(jobMapper::toResponse);
//
//}

@Override
public Page<JobResponseDTO> getJobs(
        String title,
        String city,
        EmploymentType employmentType,
        ExperienceLevel experienceLevel,
        Pageable pageable) {

    Page<Job> jobs;

    if (title != null && !title.isBlank()) {

        jobs = jobRepository.findByStatusAndTitleContainingIgnoreCase(
                JobStatus.OPEN,
                title,
                pageable);

    } else if (city != null && !city.isBlank()) {

        jobs = jobRepository.findByStatusAndCityIgnoreCase(
                JobStatus.OPEN,
                city,
                pageable);

    } else if (employmentType != null) {

        jobs = jobRepository.findByStatusAndEmploymentType(
                JobStatus.OPEN,
                employmentType,
                pageable);

    } else if (experienceLevel != null) {

        jobs = jobRepository.findByStatusAndExperienceLevel(
                JobStatus.OPEN,
                experienceLevel,
                pageable);

    } else {

        jobs = jobRepository.findByStatus(
                JobStatus.OPEN,
                pageable);
    }

    return jobs.map(jobMapper::toResponse);
}
@Override
    public JobResponseDTO getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found."));

        if (job.getStatus() != JobStatus.OPEN) {

            throw new RuntimeException(
                    "Job is no longer available.");
        }

        return jobMapper.toResponse(job);
    }
//    ==================== HELPER METHOD =======================
private Job getRecruiterJob(Long jobId) {
    RecruiterProfile recruiterProfile =
            currentUserService.getCurrentRecruiter();

    Job job = jobRepository.findById(jobId)
            .orElseThrow(() ->
                    new RuntimeException("Job not found."));

    if (!job.getRecruiterProfile().getId()
            .equals(recruiterProfile.getId())) {

        throw new RuntimeException(
                "You are not allowed to access this job.");
    }

    return job;
}
}