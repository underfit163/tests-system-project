package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.JwtDto;
import com.underfit.testsystembackend.dto.LoginDto;

public interface AuthenticationService {
    JwtDto login(LoginDto loginDto);
}
