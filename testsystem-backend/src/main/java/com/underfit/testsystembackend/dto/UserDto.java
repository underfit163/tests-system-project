package com.underfit.testsystembackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.underfit.testsystembackend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.underfit.testsystembackend.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {
    private String name;
    private String login;
    private String password;
    private Role role;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startWork;
    private Long workNumber;
}