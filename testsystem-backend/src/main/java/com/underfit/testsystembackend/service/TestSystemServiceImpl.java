package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.*;
import com.underfit.testsystembackend.entity.Answer;
import com.underfit.testsystembackend.entity.Option;
import com.underfit.testsystembackend.entity.Result;
import com.underfit.testsystembackend.mapper.*;
import com.underfit.testsystembackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TestSystemServiceImpl implements TestSystemService {

    private final OptionRepository optionRepository;

    private final AnswerMapper answerMapper;

    private final AnswerRepository answerRepository;

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
        if (createResultDto.getAcceptResult() == null) createResultDto.setAcceptResult(true);
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

    @Transactional
    @Override
    public void acceptResult(Long resultId, Boolean accept) {
        log.info("Accept result={} test: {}", resultId, accept);
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Теста с идентификатором %s не существует"
                        .formatted(resultId)));
        result.setAcceptResult(accept);
        resultRepository.save(result);
    }

    @Transactional
    @Override
    public List<AnswerDto> createAnswers(List<CreateAnswerDto> createAnswers) {
        log.info("Create answers: {}", createAnswers);

        // Получаем список идентификаторов вариантов ответов
        List<Long> optionIds = createAnswers.stream()
                .map(CreateAnswerDto::getOptionId)
                .collect(Collectors.toList());

        // Получаем все объекты Option из базы данных по их идентификаторам
        List<Option> options = optionRepository.findAllById(optionIds);

        // Создаем хэш-карту для быстрого поиска по идентификатору варианта ответа
        Map<Long, Option> optionMap = options.stream()
                .collect(Collectors.toMap(Option::getId, Function.identity()));

        // Находим результат
        Long resultId = createAnswers.get(0).getResultId();
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Результата с идентификатором %s не существует"
                        .formatted(resultId)));

        // Создаем объекты Answer и сохраняем их в базе данных
        List<Answer> answers = createAnswers.stream()
                .map(answerDto -> {
                    Answer answer = answerMapper.toEntity(answerDto);
                    answer.setResult(result);
                    // Ищем соответствующий объект Option по идентификатору
                    Option option = optionMap.get(answerDto.getOptionId());
                    if (option == null) {
                        throw new EntityNotFoundException("Варианта ответа с идентификатором %s не существует"
                                .formatted(answerDto.getOptionId()));
                    }
                    answer.setOption(option);
                    return answer;
                })
                .toList();
        answerRepository.saveAll(answers);
        // Преобразуем объекты Answer в DTO и возвращаем список DTO ответов
        return answers.stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerDto> getAnswersByResultId(Long resultId) {
        log.info("Get answers by result id={}", resultId);
        return answerRepository.findByResult_Id(resultId, Sort.by(Sort.Order.asc("id")))
                .stream()
                .map(answerMapper::toDto).toList();
    }
}
