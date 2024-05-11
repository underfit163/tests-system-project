package com.underfit.testsystembackend.controller;

import com.underfit.testsystembackend.dto.CreateResultDto;
import com.underfit.testsystembackend.service.TestSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/test-system")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TestSystemController {

    private final TestSystemService testSystemService;

    @GetMapping("/tests")
    public ResponseEntity<?> findTests() {
        return ResponseEntity.ok(testSystemService.getTests());
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<?> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testSystemService.getTestById(id));
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(testSystemService.getQuestionById(id));
    }
    @PreAuthorize("hasRole('ADMIN') or #createResultDto.userId == authentication.principal.id")
    @PostMapping("/create/result")
    public ResponseEntity<?> createResult(@RequestBody CreateResultDto createResultDto) {
        return ResponseEntity.ok(testSystemService.createResult(createResultDto));
    }

    @GetMapping("/assessment/{testId}/{score}")
    public ResponseEntity<?> getAssessmentByTestIdAndScore(@PathVariable Long testId, @PathVariable Integer score) {
        return ResponseEntity.ok(testSystemService.getAssessmentByTestIdAndScore(testId, score));
    }

    @GetMapping("/accept/result/{id}/{accept}")
    public void acceptResult(@PathVariable Long id, @PathVariable Boolean accept) {
        testSystemService.acceptResult(id, accept);
    }
}

