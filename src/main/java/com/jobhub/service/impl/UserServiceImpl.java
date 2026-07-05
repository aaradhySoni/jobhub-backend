package com.jobhub.service.impl;

import com.jobhub.dto.request.LoginRequestDTO;
import com.jobhub.dto.request.RegistrationRequestDTO;
import com.jobhub.dto.response.LoginResponseDTO;
import com.jobhub.dto.response.RegistrationResponseDTO;
import com.jobhub.entity.User;
import com.jobhub.exception.EmailAlreadyExistsException;
import com.jobhub.repository.UserRepository;
import com.jobhub.security.CustomUserDetailsService;
import com.jobhub.security.JwtService;
import com.jobhub.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Override
    public RegistrationResponseDTO registerUser(RegistrationRequestDTO requestDTO) {

        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .phone(requestDTO.getPhone())
                .role(requestDTO.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return RegistrationResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .message("User registered successfully.")
                .build();
    }

        @Override
        public LoginResponseDTO loginUser(LoginRequestDTO requestDTO) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getEmail(),
                            requestDTO.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(requestDTO.getEmail());

            String token = jwtService.generateToken(userDetails);

            return LoginResponseDTO.builder()
                    .token(token)
                    .message("Login successful")
                    .build();
        }
    }
