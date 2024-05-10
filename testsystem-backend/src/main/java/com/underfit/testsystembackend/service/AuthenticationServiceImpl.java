package com.underfit.testsystembackend.service;

import com.underfit.testsystembackend.dto.JwtDto;
import com.underfit.testsystembackend.dto.LoginDto;
import com.underfit.testsystembackend.security.JwtUtils;
import com.underfit.testsystembackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;//занимается аутентификацией пользователя
    private final JwtUtils jwtUtils;
    @Override
    public JwtDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));//здесь идет проверка что все хорошо
        SecurityContextHolder.getContext().setAuthentication(authentication);//Что это такое? контекст для секюрити(контейнер куда помещаем данные)
        //в principal(UserDetailsImpl) хранится информация о нашем пользователе
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .findFirst().orElse(null);
        return new JwtDto(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), role);
    }
}
