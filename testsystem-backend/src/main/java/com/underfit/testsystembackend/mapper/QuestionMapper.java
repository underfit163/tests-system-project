package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.QuestionWithOptionDto;
import com.underfit.testsystembackend.entity.Question;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OptionMapper.class})
public interface QuestionMapper {
    Question toEntity(QuestionWithOptionDto questionWithOptionDto);

    @AfterMapping
    default void linkOptions(@MappingTarget Question question) {
        question.getOptions().forEach(option -> option.setQuestion(question));
    }

    QuestionWithOptionDto toDto(Question question);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Question partialUpdate(QuestionWithOptionDto questionWithOptionDto, @MappingTarget Question question);
}