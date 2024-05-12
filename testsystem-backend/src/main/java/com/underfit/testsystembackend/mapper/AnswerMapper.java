package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.AnswerDto;
import com.underfit.testsystembackend.dto.CreateAnswerDto;
import com.underfit.testsystembackend.entity.Answer;
import com.underfit.testsystembackend.entity.Option;
import com.underfit.testsystembackend.entity.Result;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ResultMapper.class, OptionMapper.class})
public interface AnswerMapper {
    Answer toEntity(AnswerDto answerDto);

    AnswerDto toDto(Answer answer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Answer partialUpdate(AnswerDto answerDto, @MappingTarget Answer answer);

    @Mapping(source = "optionId", target = "option.id")
    @Mapping(source = "resultId", target = "result.id")
    Answer toEntity(CreateAnswerDto createAnswerDto);

    @InheritInverseConfiguration(name = "toEntity")
    CreateAnswerDto toCreateAnswerDto(Answer answer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "optionId", target = "option")
    @Mapping(source = "resultId", target = "result")
    Answer partialUpdate(CreateAnswerDto createAnswerDto, @MappingTarget Answer answer);

    default Result createResult(Long resultId) {
        if (resultId == null) {
            return null;
        }
        Result result = new Result();
        result.setId(resultId);
        return result;
    }

    default Option createOption(Long optionId) {
        if (optionId == null) {
            return null;
        }
        Option option = new Option();
        option.setId(optionId);
        return option;
    }
}