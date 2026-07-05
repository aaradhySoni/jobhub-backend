package com.jobhub.service.service;

import com.jobhub.dto.request.LoginRequestDTO;
import com.jobhub.dto.request.RegistrationRequestDTO;
import com.jobhub.dto.response.LoginResponseDTO;
import com.jobhub.dto.response.RegistrationResponseDTO;

public interface UserService {
    RegistrationResponseDTO registerUser(RegistrationRequestDTO requestDTO);

    LoginResponseDTO loginUser(LoginRequestDTO requestDTO);
}
