package com.underfit.testsystembackend.controller;

import com.underfit.testsystembackend.dto.LoginDto;
import com.underfit.testsystembackend.dto.RegistrationUserDto;
import com.underfit.testsystembackend.entity.Role;
import com.underfit.testsystembackend.mapper.UserMapper;
import com.underfit.testsystembackend.repository.UserRepository;
import com.underfit.testsystembackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    private final PasswordEncoder encoder;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authenticationService.login(loginDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (userRepository.existsByLogin(registrationUserDto.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username is already exist!");
        }
        if (userRepository.existsByEmail(registrationUserDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email is already exist!");
        }
        if(registrationUserDto.getRole() == null) {
            registrationUserDto.setRole(Role.ROLE_USER);
        }
        registrationUserDto.setPassword(encoder.encode(registrationUserDto.getPassword()));
        userRepository.save(userMapper.toEntity(registrationUserDto));
        return ResponseEntity.ok("User registered successfully!");
    }
}
