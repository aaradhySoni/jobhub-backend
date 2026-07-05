package com.jobhub.controller;

import com.jobhub.dto.request.LoginRequestDTO;
import com.jobhub.dto.request.RegistrationRequestDTO;
import com.jobhub.dto.response.LoginResponseDTO;
import com.jobhub.dto.response.RegistrationResponseDTO;
import com.jobhub.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //Marks this class as a REST API Controller.
@RequestMapping("/api/auth")//Sets the base URL.
@RequiredArgsConstructor //Lombok automatically creates a constructor for all final fields.
public class AuthController {
    //DI injection
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> registerUser(@Valid @RequestBody RegistrationRequestDTO requestDTO){
        RegistrationResponseDTO response = userService.registerUser(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    //@RequestBody - Converts incoming JSON into a Java object.
    //@Valid - This tells Spring: "Before calling this method, validate the DTO."
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO requestDTO) {
        LoginResponseDTO response = userService.loginUser(requestDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/test")
    public String testLogin(){
        return "test successful beta";
    }

}
