package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.OptionDto;
import com.underfit.testsystembackend.entity.Option;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OptionMapper {
    Option toEntity(OptionDto optionDto);

    OptionDto toDto(Option option);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Option partialUpdate(OptionDto optionDto, @MappingTarget Option option);
}