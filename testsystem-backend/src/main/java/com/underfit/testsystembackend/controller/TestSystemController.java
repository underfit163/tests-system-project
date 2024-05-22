package com.underfit.testsystembackend.controller;

import com.underfit.testsystembackend.dto.CreateAnswerDto;
import com.underfit.testsystembackend.dto.CreateResultDto;
import com.underfit.testsystembackend.dto.ResultDto;
import com.underfit.testsystembackend.service.TestSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/test-system")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TestSystemController {

    private final TestSystemService testSystemService;

    @GetMapping("/tests")
    public ResponseEntity<?> getTests() {
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
        ResultDto resultDto = testSystemService.createResult(createResultDto);
        resultDto.setAssessment(testSystemService
                .getAssessmentByTestIdAndScore(resultDto.getTest().getId(), resultDto.getScore()));
        return ResponseEntity.ok(resultDto);
    }

    @GetMapping("/assessment/{testId}/{score}")
    public ResponseEntity<?> getAssessmentByTestIdAndScore(@PathVariable Long testId, @PathVariable Integer score) {
        return ResponseEntity.ok(testSystemService.getAssessmentByTestIdAndScore(testId, score));
    }

    @GetMapping("/accept/result/{id}/{accept}")
    public void acceptResult(@PathVariable Long id, @PathVariable Boolean accept) {
        testSystemService.acceptResult(id, accept);
    }

    @PreAuthorize("""
            hasRole('ADMIN') or @resultRepository.findById(#createAnswerDtoList.get(0).resultId).orElseThrow().user.id == authentication.principal.id""")
    @PostMapping("/create/answers")
    public ResponseEntity<?> createAnswers(@RequestBody List<CreateAnswerDto> createAnswerDtoList) {
        return ResponseEntity.ok(testSystemService.createAnswers(createAnswerDtoList));
    }

    @PreAuthorize("""
            hasRole('ADMIN') or @resultRepository.findById(#resultId).orElseThrow().user.id == authentication.principal.id""")
    @GetMapping("/answers/result/{resultId}")
    public ResponseEntity<?> getAnswersByResultId(@PathVariable Long resultId) {
        return ResponseEntity.ok(testSystemService.getAnswersByResultId(resultId));
    }
}

