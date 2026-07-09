package com.jobhub.security;

import com.jobhub.entity.JobSeekerProfile;
import com.jobhub.entity.RecruiterProfile;
import com.jobhub.entity.User;

public interface CurrentUserService {

    User getCurrentUser();

    JobSeekerProfile getCurrentJobSeeker();

    RecruiterProfile getCurrentRecruiter();
}