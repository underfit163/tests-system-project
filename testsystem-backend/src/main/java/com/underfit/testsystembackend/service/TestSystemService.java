package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.*;

import java.util.List;

public interface TestSystemService {
    /**
     * Получение всех тестов системы
     *
     * @return тесты
     */
    List<TestDto> getTests();

    /**
     * Получение теста по id, связанных вопросов и вариантов ответов к ним
     *
     * @param id теста
     * @return Тест и вопросы, и варианты ответа к нему
     */
    TestWithQuestionDto getTestById(Long id);

    /**
     * Получение вопроса по id вопроса и ответов к нему
     *
     * @param id вопроса
     * @return вопрос с вариантами ответа
     */
    QuestionWithOptionDto getQuestionById(Long id);

    /**
     * Сохранение результата теста для пользователя
     *
     * @param createResultDto Результат пользователя по тесту
     * @return Сохраненный результат
     */
    ResultDto createResult(CreateResultDto createResultDto);

    /**
     * Получение критерия оценивания за тест по баллам, которые набрал за тест пользователь
     *
     * @param testId тест
     * @param score  набранные баллы
     * @return критерий оценивания (результат)
     */
    AssessmentDto getAssessmentByTestIdAndScore(Long testId, Integer score);

    /**
     * Принятие результата теста или нет
     *
     * @param resultId Результат теста
     * @param accept   Принять/отклонить
     */
    void acceptResult(Long resultId, Boolean accept);

    /**
     * Сохранение ответов пользователя на вопросы теста
     * @param createAnswers ответы
     * @return результат сохранения ответов
     */
    List<AnswerDto> createAnswers(List<CreateAnswerDto> createAnswers);

    /**
     * Получение ответов на тест для пользователя(по resultId)
     * @param resultId Результат теста
     * @return Ответы
     */
    List<AnswerDto> getAnswersByResultId(Long resultId);
}
