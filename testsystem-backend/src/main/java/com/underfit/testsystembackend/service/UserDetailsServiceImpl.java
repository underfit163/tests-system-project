package com.underfit.testsystembackend.service;


import com.underfit.testsystembackend.entity.User;
import com.underfit.testsystembackend.repository.UserRepository;
import com.underfit.testsystembackend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username).orElseThrow(()->
                new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsImpl.fromUser(user);
    }
}
