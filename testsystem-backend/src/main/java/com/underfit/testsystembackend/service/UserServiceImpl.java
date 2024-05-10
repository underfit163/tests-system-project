package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.ResultDto;
import com.underfit.testsystembackend.dto.UserWithTicketDto;
import com.underfit.testsystembackend.mapper.ResultMapper;
import com.underfit.testsystembackend.mapper.UserMapper;
import com.underfit.testsystembackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

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
}
