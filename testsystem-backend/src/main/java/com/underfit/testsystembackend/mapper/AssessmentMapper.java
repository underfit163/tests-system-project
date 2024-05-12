package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.AssessmentDto;
import com.underfit.testsystembackend.dto.CreateAssessmentDto;
import com.underfit.testsystembackend.entity.Assessment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessmentMapper {
    Assessment toEntity(AssessmentDto assessmentDto);

    AssessmentDto toDto(Assessment assessment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Assessment partialUpdate(AssessmentDto assessmentDto, @MappingTarget Assessment assessment);

    Assessment toEntity(CreateAssessmentDto createAssessmentDto);

    CreateAssessmentDto toCreateAssessmentDto(Assessment assessment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Assessment partialUpdate(CreateAssessmentDto createAssessmentDto, @MappingTarget Assessment assessment);
}