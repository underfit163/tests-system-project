package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.*;
import com.underfit.testsystembackend.entity.Result;
import com.underfit.testsystembackend.mapper.AssessmentMapper;
import com.underfit.testsystembackend.mapper.QuestionMapper;
import com.underfit.testsystembackend.mapper.ResultMapper;
import com.underfit.testsystembackend.mapper.TestMapper;
import com.underfit.testsystembackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TestSystemServiceImpl implements TestSystemService {

    private final UserRepository userRepository;

    private final AssessmentMapper assessmentMapper;

    private final AssessmentRepository assessmentRepository;

    private final ResultRepository resultRepository;

    private final ResultMapper resultMapper;

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public List<TestDto> getTests() {
        log.info("Get all tests");
        return testRepository.findAll(Sort.by("testName"))
                .stream().map(testMapper::toDto).toList();
    }

    @Override
    public TestWithQuestionDto getTestById(Long id) {
        log.info("Get test by id={}", id);
        return testMapper.toWithQuestionDto(testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Теста с идентификатором %s не существует"
                        .formatted(id))));
    }

    @Override
    public QuestionWithOptionDto getQuestionById(Long id) {
        log.info("Get question by id={}", id);
        return questionMapper.toDto(questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Вопроса с идентификатором %s не существует"
                        .formatted(id))));
    }

    @Transactional
    @Override
    public ResultDto createResult(CreateResultDto createResultDto) {
        log.info("Result before mapping: {}", createResultDto);
        Result result = resultMapper.toEntity(createResultDto);
        result.setUser(userRepository
                .findById(createResultDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с идентификатором %s не существует"
                        .formatted(createResultDto.getUserId()))));
        result.setTest(testRepository.findById(createResultDto.getTestId())
                .orElseThrow(() -> new EntityNotFoundException("Теста с идентификатором %s не существует"
                        .formatted(createResultDto.getUserId()))));
        return resultMapper.toDto(resultRepository.save(result));
    }

    @Override
    public AssessmentDto getAssessmentByTestIdAndScore(Long testId, Integer score) {
        return assessmentMapper.toDto(assessmentRepository
                .findAssessmentByTestIdAndScore(testId, score)
                .orElseThrow(() -> new EntityNotFoundException("Критерия c набранным количеством очков=%s для указанного теста с id=%s не существует"
                        .formatted(score, testId))));
    }

    @Override
    public void acceptResult(Long resultId, Boolean accept) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Теста с идентификатором %s не существует"
                        .formatted(resultId)));
        result.setAcceptResult(accept);
        resultRepository.save(result);
    }
}
