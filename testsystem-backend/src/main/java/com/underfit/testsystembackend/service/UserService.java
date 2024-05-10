package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.ResultDto;
import com.underfit.testsystembackend.dto.UserWithTicketDto;

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
}
