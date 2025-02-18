package com.showroommanagement_jwt.filter;

import com.showroommanagement_jwt.entity.User;
import com.showroommanagement_jwt.service.JWTService;
import com.showroommanagement_jwt.service.MyUserDetailsService;
import com.showroommanagement_jwt.util.UserCredentialVerification;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final UserCredentialVerification userCredentialVerification;
    private final JWTService jwtService;
    private final MyUserDetailsService myUserDetailsService;

    public JWTFilter(final UserCredentialVerification userCredentialVerification, final JWTService jwtService, final MyUserDetailsService myUserDetailsService) {
        this.userCredentialVerification = userCredentialVerification;
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final User user = userCredentialVerification.userInfo();
        if (user != null) {
            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmailId());
            if (jwtService.validateToken(request.getHeader("Authorization").substring(7), userDetails)) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}