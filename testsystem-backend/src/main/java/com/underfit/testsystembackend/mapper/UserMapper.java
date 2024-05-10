package com.underfit.testsystembackend.mapper;

import com.underfit.testsystembackend.dto.LoginDto;
import com.underfit.testsystembackend.dto.UserDto;
import com.underfit.testsystembackend.dto.UserWithTicketDto;
import com.underfit.testsystembackend.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TicketMapper.class})
public interface UserMapper {
    User toEntity(LoginDto loginDto);

    LoginDto toLoginDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(LoginDto loginDto, @MappingTarget User user);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);

    User toEntity(UserWithTicketDto userWithTicketDto);

    @AfterMapping
    default void linkTickets(@MappingTarget User user) {
        user.getTickets().forEach(ticket -> ticket.setUser(user));
    }

    UserWithTicketDto toTicketWithUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserWithTicketDto userWithTicketDto, @MappingTarget User user);
}