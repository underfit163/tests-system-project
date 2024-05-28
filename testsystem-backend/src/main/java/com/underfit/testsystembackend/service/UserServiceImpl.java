package com.underfit.testsystembackend.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.underfit.testsystembackend.dto.*;
import com.underfit.testsystembackend.entity.Result;
import com.underfit.testsystembackend.entity.Test;
import com.underfit.testsystembackend.mapper.ResultMapper;
import com.underfit.testsystembackend.mapper.TestMapper;
import com.underfit.testsystembackend.mapper.UserMapper;
import com.underfit.testsystembackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final AssessmentRepository assessmentRepository;

    private final OptionRepository optionRepository;

    private final QuestionRepository questionRepository;

    private final TestMapper testMapper;

    private final TestRepository testRepository;

    private final ResultRepository resultRepository;

    private final UserMapper userMapper;
    private final ResultMapper resultMapper;
    private final UserRepository userRepository;

    @Override
    public UserWithTicketDto getUserInfo(Long id) {
        log.info("Get user by id = {}", id);
        return userMapper.toTicketWithUserDto(userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с идентификатором %s не существует"
                        .formatted(id))));
    }

    @Override
    public List<ResultDto> getResultsByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с идентификатором %s не существует"
                        .formatted(id)))
                .getResults().stream().map(resultMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ResultDto> getResultsByTestId(Long testId) {
        log.info("Get results by test id = {}", testId);
        return resultRepository.findByTest_Id(testId, Sort.by(Sort.Order.asc("user.id"),
                        Sort.Order.desc("id")))
                .stream().map(resultMapper::toDto).toList();
    }

    @Transactional
    @Override
    public CreateTestDto createTest(CreateTestDto testDto) {
        log.info("Create test: {}", testDto);
        Test savedTest = testRepository.save(testMapper.toEntity(testDto));
        savedTest.setAssessments(new HashSet<>(assessmentRepository.saveAll(savedTest.getAssessments())));
        savedTest.setQuestions(savedTest.getQuestions()
                .stream()
                .map(questionRepository::save)
                .peek(question -> question
                        .setOptions(new HashSet<>(optionRepository.saveAll(question.getOptions()))))
                .collect(Collectors.toSet()));
        return testMapper.toCreateTestDto(savedTest);
    }

    @Transactional
    @Override
    public List<ResultDto> createResultsFromCsv(MultipartFile file) throws IOException, CsvValidationException {
        if (file.isEmpty()) throw new FileNotFoundException("Please upload a CSV file");
        if (!Objects.requireNonNull(file.getContentType()).endsWith("csv"))
            throw new CsvValidationException("File format not CSV");
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVReader csvReader = new CSVReaderBuilder(reader).build();
        String[] record;
        List<Result> results = new ArrayList<>();
        while ((record = csvReader.readNext()) != null) {
            CreateResultDto createResultDto = new CreateResultDto();
            createResultDto.setScore(Integer.parseInt(record[0]));
            createResultDto.setAcceptResult(((record[1] != null) && (!record[1].isEmpty())) ? Boolean.parseBoolean(record[1]) : null);
            createResultDto.setUserId(Long.parseLong(record[2]));
            createResultDto.setTestId(Long.parseLong(record[3]));
            Result result = resultMapper.toEntity(createResultDto);
            result.setUser(userRepository
                    .findById(createResultDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Пользователя с идентификатором %s не существует"
                            .formatted(createResultDto.getUserId()))));
            result.setTest(testRepository.findById(createResultDto.getTestId())
                    .orElseThrow(() -> new EntityNotFoundException("Теста с идентификатором %s не существует"
                            .formatted(createResultDto.getUserId()))));
            results.add(result);
        }
        csvReader.close();
        return resultRepository.saveAll(results).stream().map(resultMapper::toDto).toList();

    }

    @Override
    public List<ResultDto> getResultByFilter(ResultFilter resultFilter) {
        log.info("Filter for result: {}", resultFilter);
        List<Result> results = resultRepository.findAll(resultFilter.toSpecification());
        return results.stream()
                .map(resultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll(Sort.by("id")).stream().map(userMapper::toDto).toList();
    }
}
