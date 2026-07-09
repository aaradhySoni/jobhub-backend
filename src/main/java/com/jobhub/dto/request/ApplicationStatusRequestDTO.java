package com.jobhub.dto.request;

import com.jobhub.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusRequestDTO {

    @NotNull
    private ApplicationStatus status;
}