package com.underfit.testsystembackend.controller;

import com.underfit.testsystembackend.dto.LoginDto;
import com.underfit.testsystembackend.dto.UserDto;
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
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username is already exist!");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email is already exist!");
        }
        if(userDto.getRole() == null) {
            userDto.setRole(Role.ROLE_USER);
        }
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userMapper.toEntity(userDto));
        return ResponseEntity.ok("User registered successfully!");
    }
}
