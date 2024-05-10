package com.underfit.testsystembackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.underfit.testsystembackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link com.underfit.testsystembackend.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWithTicketDto implements Serializable {
    private Long id;
    private String name;
    private String login;
    private Role role;
    private String email;
    private LocalDate birthday;
    private LocalDate startWork;
    private Long workNumber;
    private Set<TicketDto> tickets = new LinkedHashSet<>();
}