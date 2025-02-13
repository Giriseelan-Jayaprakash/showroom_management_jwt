package com.showroommanagement_jwt.config;

import com.showroommanagement_jwt.filter.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    public SecurityConfiguration(final UserDetailsService userDetailsService, final JWTFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> request./*requestMatchers("/**").permitAll().*/anyRequest().authenticated()) // only authenticated person will be permited
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
//                        .requestMatchers("/api/v1/users/create-sales-manager").hasAnyAuthority("ROLE_ADMIN")
//                        .requestMatchers("/api/v1/users/create-admin").permitAll()
//                        .requestMatchers("/api/v1/users/create-salesman").hasAnyAuthority("ROLE_ADMIN", "ROLE_SALES_MANAGER")
//                        .requestMatchers("/api/v1/users/create-customer").hasAnyAuthority("ROLE_ADMIN", "ROLE_SALES_MANAGER", "ROLE_SALESMAN")
//                        .requestMatchers("/api/v1/users/refresh-token").permitAll()
//                        .requestMatchers("/api/v1/users/login").permitAll()
//                        .requestMatchers("/api/v1/users/**").permitAll()
//                        .requestMatchers("/api/v1/admin/**").hasAnyAuthority("ROLE_ADMIN")
//                        .requestMatchers("/api/v1/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//
//                        .requestMatchers("/api/v1/bike/**").permitAll()
//                        .requestMatchers("/api/v1/customer/**").hasAnyAuthority("ROLE_SALES_MANAGER", "ROLE_ADMIN,ROLE_SALESMAN")
//                        .requestMatchers("/api/v1/sales/**").hasAnyAuthority("ROLE_SALES_MANAGER", "ROLE_ADMIN,ROLE_SALESMAN")
//                        .requestMatchers("/api/v1/sales-manager/**").hasAnyAuthority("ROLE_SALES_MANAGER", "ROLE_ADMIN")
//                        .requestMatchers("/api/v1/salesman/**").hasAnyAuthority("ROLE_SALES_MANAGER", "ROLE_ADMIN", "ROLE_SALESMAN")
//                        .requestMatchers("/api/v1/showroom/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SALES_MANAGER")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) // to use form based login for in-build
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // for storing password as encrypted
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean //using JWT
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityContextHolder securityContextHolder(){
        return new SecurityContextHolder();
    }
}

