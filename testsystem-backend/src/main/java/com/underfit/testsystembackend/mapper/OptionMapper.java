package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.CreateOptionDto;
import com.underfit.testsystembackend.dto.OptionDto;
import com.underfit.testsystembackend.entity.Option;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuestionMapper.class})
public interface OptionMapper {
    Option toEntity(OptionDto optionDto);

    OptionDto toDto(Option option);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Option partialUpdate(OptionDto optionDto, @MappingTarget Option option);

    Option toEntity(CreateOptionDto createOptionDto);

    CreateOptionDto toCreateOptionDto(Option option);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Option partialUpdate(com.underfit.testsystembackend.dto.CreateOptionDto createOptionDto, @MappingTarget Option option);
}