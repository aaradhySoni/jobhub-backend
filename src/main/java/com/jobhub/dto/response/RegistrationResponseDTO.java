package com.jobhub.dto.response;

import com.jobhub.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private String message;
}
