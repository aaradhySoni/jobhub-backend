package com.jobhub.service.service;

import com.jobhub.dto.response.SavedJobResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SavedJobService {

//   method to mark a job as "savedJob".
    void saveJob(Long jobId);

//   method to get all the jobs we have saved.
    Page<SavedJobResponseDTO> getMySavedJobs(Pageable pageable);

//   method to mark a job (remove) from savedJob.
    void removeSavedJob(Long jobId);

//   to check whether a job is already saved.
    boolean isJobSaved(Long jobId);
}