package com.jobhub.dto.response;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponseDTO {

    private Long id;

    private String institutionName;

    private String degree;

    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double percentage;

    private String description;

    private Boolean currentlyStudying;
}
