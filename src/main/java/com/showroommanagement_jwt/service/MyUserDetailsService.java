package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        return this.userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found !!!"));
    }
}