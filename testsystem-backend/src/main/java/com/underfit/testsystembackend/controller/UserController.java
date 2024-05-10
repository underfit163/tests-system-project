package com.underfit.testsystembackend.controller;

import com.underfit.testsystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}/info")
    public ResponseEntity<?> getInfoAboutUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}/results")
    public ResponseEntity<?> getResultsTestByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getResultsByUserId(id));
    }
}

