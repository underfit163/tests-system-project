package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.TestDto;
import com.underfit.testsystembackend.dto.TestWithQuestionDto;
import com.underfit.testsystembackend.entity.Test;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuestionMapper.class})
public interface TestMapper {
    Test toEntity(TestDto testDto);

    TestDto toDto(Test test);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Test partialUpdate(TestDto testDto, @MappingTarget Test test);

    Test toEntity(TestWithQuestionDto testWithQuestionDto);

    @AfterMapping
    default void linkQuestions(@MappingTarget Test test) {
        test.getQuestions().forEach(question -> question.setTest(test));
    }

    TestWithQuestionDto toWithQuestionDto(Test test);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Test partialUpdate(TestWithQuestionDto testWithQuestionDto, @MappingTarget Test test);
}