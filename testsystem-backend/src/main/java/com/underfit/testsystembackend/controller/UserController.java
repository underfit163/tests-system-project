package com.underfit.testsystembackend.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.underfit.testsystembackend.dto.CreateTestDto;
import com.underfit.testsystembackend.dto.ResultDto;
import com.underfit.testsystembackend.dto.ResultFilter;
import com.underfit.testsystembackend.service.TestSystemService;
import com.underfit.testsystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final TestSystemService testSystemService;

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}/info")
    public ResponseEntity<?> getInfoAboutUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}/results")
    public ResponseEntity<?> getResultsTestByUserId(@PathVariable Long id) {
        List<ResultDto> resultDtoList = userService.getResultsByUserId(id);
        setAssessmentResults(resultDtoList);
        return ResponseEntity.ok(resultDtoList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test/{id}/results")
    public ResponseEntity<?> getResultsByTestId(@PathVariable Long id) {
        List<ResultDto> resultDtoList = userService.getResultsByTestId(id);
        setAssessmentResults(resultDtoList);
        return ResponseEntity.ok(userService.getResultsByTestId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/test")
    public ResponseEntity<?> createTest(@RequestBody CreateTestDto createTestDto) {
        return ResponseEntity.ok(userService.createTest(createTestDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/results/upload")
    public ResponseEntity<?> createResultsFromCsv(@RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
        List<ResultDto> resultDtoList = userService.createResultsFromCsv(file);
        setAssessmentResults(resultDtoList);
        return ResponseEntity.ok(resultDtoList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/filter/results")
    public ResponseEntity<?> filterResults(@RequestBody ResultFilter resultFilter) {
        List<ResultDto> resultDtoList = userService.getResultByFilter(resultFilter);
        setAssessmentResults(resultDtoList);
        return ResponseEntity.ok(resultDtoList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    private void setAssessmentResults(List<ResultDto> resultDtoList) {
        resultDtoList.forEach(resultDto ->
                resultDto.setAssessment(testSystemService
                        .getAssessmentByTestIdAndScore(resultDto.getTest().getId(),
                                resultDto.getScore())));
    }
}

