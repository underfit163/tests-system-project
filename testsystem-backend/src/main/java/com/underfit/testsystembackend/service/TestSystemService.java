package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.*;

import java.util.List;

public interface TestSystemService {
    /**
     * Получение всех тестов системы
     * @return тесты
     */
    List<TestDto> getTests();

    /**
     * Получение теста по id, связанных вопросов и вариантов ответов к ним
     * @param id теста
     * @return Тест и вопросы, и варианты ответа к нему
     */
    TestWithQuestionDto getTestById(Long id);

    /**
     * Получение вопроса по id вопроса и ответов к нему
     * @param id вопроса
     * @return вопрос с вариантами ответа
     */
    QuestionWithOptionDto getQuestionById(Long id);

    /**
     * Сохранение результатов теста для пользователя
     * @param createResultDto Результат пользователя по тесту
     * @return Сохраненный результат
     */
    ResultDto createResult(CreateResultDto createResultDto);

    AssessmentDto getAssessmentByTestIdAndScore(Long testId, Integer score);
}
