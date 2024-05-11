package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.CreateResultDto;
import com.underfit.testsystembackend.dto.ResultDto;
import com.underfit.testsystembackend.entity.Result;
import com.underfit.testsystembackend.entity.Test;
import com.underfit.testsystembackend.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, TestMapper.class})
public interface ResultMapper {
    Result toEntity(ResultDto resultDto);

    ResultDto toDto(Result result);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Result partialUpdate(ResultDto resultDto, @MappingTarget Result result);

    @Mapping(source = "testId", target = "test.id")
    @Mapping(source = "userId", target = "user.id")
    Result toEntity(CreateResultDto createResultDto);

    @InheritInverseConfiguration(name = "toEntity")
    CreateResultDto toCreateResultDto(Result result);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "testId", target = "test")
    @Mapping(source = "userId", target = "user")
    Result partialUpdate(CreateResultDto createResultDto, @MappingTarget Result result);

    default User createUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Test createTest(Long testId) {
        if (testId == null) {
            return null;
        }
        Test test = new Test();
        test.setId(testId);
        return test;
    }
}