package com.jobhub.dto.response;

import com.jobhub.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentApplicationResponseDTO {

    private Long applicationId;

    private String applicantName;

    private String jobTitle;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
}
/*Why another DTO?

Remember what we learned?

One DTO = One Use Case

Don't reuse ApplicantResponseDTO.

Because here we don't need:

Resume URL ❌
Cover Letter ❌
City ❌
State ❌

Dashboard should be lightweight.*/