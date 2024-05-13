package com.underfit.testsystembackend.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.underfit.testsystembackend.dto.CreateTestDto;
import com.underfit.testsystembackend.dto.ResultFilter;
import com.underfit.testsystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test/{id}/results")
    public ResponseEntity<?> getResultsByTestId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getResultsByTestId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/test")
    public ResponseEntity<?> getResultsByTestId(@RequestBody CreateTestDto createTestDto) {
        return ResponseEntity.ok(userService.createTest(createTestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/results/upload")
    public ResponseEntity<?> createResultsFromCsv(@RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
        return ResponseEntity.ok(userService.createResultsFromCsv(file));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/filter/results")
    public ResponseEntity<?> filterResults(@RequestBody ResultFilter resultFilter) {
        return ResponseEntity.ok(userService.getResultByFilter(resultFilter));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

}

