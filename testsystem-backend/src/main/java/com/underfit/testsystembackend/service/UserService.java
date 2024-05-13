package com.underfit.testsystembackend.service;

import com.opencsv.exceptions.CsvValidationException;
import com.underfit.testsystembackend.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    /**
     * Данные о пользователе вместе с его билетами для профиля
     * @param id пользователя
     * @return Пользователь и его билеты
     */
    UserWithTicketDto getUserInfo(Long id);

    /**
     * Получение результатов теста для пользователя
     *
     * @param id пользователя
     * @return результаты
     */
    List<ResultDto> getResultsByUserId(Long id);

    /**
     * Получение результатов пользователей по тесту без карточки пользователя
     * @param testId тест
     * @return Результаты пользователей без карточки
     */
    List<ResultDto> getResultsByTestId(Long testId);

    /**
     * Создание теста с вопросами и вариантами ответа, а также критериями оценивания для админа
     * @param testDto тест с вопросами, ответами и критериями оценивания
     * @return Созданный тест
     */
    CreateTestDto createTest(CreateTestDto testDto);

    /**
     * Загрузить результат тестов через csv файл
     * @param file файл с результатами пользователей по тестам
     * @return список загруженных результатов
     * @throws IOException ошибка загрузки файла
     * @throws CsvValidationException ошибка невалидного csv файла
     */
    List<ResultDto> createResultsFromCsv(MultipartFile file) throws IOException, CsvValidationException;

    /**
     * Фильтрация по результатам для админа
     * @param resultFilter Фильтр
     * @return результаты
     */
    List<ResultDto> getResultByFilter(ResultFilter resultFilter);

    /**
     * Получить всех пользователей
     * @return Пользователи
     */
    List<UserDto> getUsers();
}
