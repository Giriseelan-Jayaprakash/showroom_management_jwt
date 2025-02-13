package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.repository.UserCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    public MyUserDetailsService(final UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        return this.userCredentialRepository.findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found !!!"));
    }
}