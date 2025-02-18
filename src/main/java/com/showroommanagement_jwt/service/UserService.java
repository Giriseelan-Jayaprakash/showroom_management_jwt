package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.entity.User;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.exception.ResourceNotFoundException;
import com.showroommanagement_jwt.exception.UnAuthorizedException;
import com.showroommanagement_jwt.exception.UserNameNotFoundException;
import com.showroommanagement_jwt.repository.UserRepository;
import com.showroommanagement_jwt.util.Authority;
import com.showroommanagement_jwt.util.Constant;
import com.showroommanagement_jwt.util.UserCredentialValidation;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserCredentialValidation userCredentialValidation;

    public UserService(final UserRepository userRepository, final JWTService jwtService, final UserCredentialValidation userCredentialValidation) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userCredentialValidation = userCredentialValidation;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Map<String, Object> createSalesManager(final User user) {
        user.setAuthority(Authority.ROLE_SALES_MANAGER);
        return createUser(user);
    }

    public Map<String, Object> createAdmin(final User user) {
        user.setAuthority(Authority.ROLE_ADMIN);
        return createUser(user);
    }

    public Map<String, Object> createSalesman(final User user) {
        user.setAuthority(Authority.ROLE_SALESMAN);
        return createUser(user);
    }

    public Map<String, Object> createCustomer(final User user) {
        user.setAuthority(Authority.ROLE_CUSTOMER);
        return createUser(user);
    }

    public Map<String, Object> createUser(final User user) {
        if (!userCredentialValidation.isValidEmail(user.getEmailId())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        if (!userCredentialValidation.isValidPassword(user.getPassword())) {
            throw new BadRequestServiceAlertException("Invalid Password format");
        }
        if (userRepository.existsByEmailId(user.getEmailId())) {
            throw new BadRequestServiceAlertException("Account Already exists");
        }
        user.setAuthority(user.getAuthority());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", user.getId());
        responseData.put("userName", user.getUserName());
        responseData.put("emailId", user.getEmailId());
        responseData.put("authority", user.getAuthority());
        responseData.put("createdAt", user.getCreatedAt());
        responseData.put("updatedAt", user.getUpdatedAt());
        return responseData;
    }

    public Map<String, Object> verifyUser(final User user) {
        if (!userCredentialValidation.isValidEmail(user.getEmailId()))
            throw new BadRequestServiceAlertException("Incorrect EmailId.");
        if (!userCredentialValidation.isValidPassword(user.getPassword()))
            throw new BadRequestServiceAlertException("Incorrect Password.");
        Optional<User> storedUser = userRepository.findByEmailId(user.getEmailId());
        if (storedUser.isEmpty()) {
            throw new UserNameNotFoundException("User not found for email:" + user.getEmailId());
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), storedUser.get().getPassword())) {
            throw new AuthorizationDeniedException("Invalid User");
        }
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", storedUser.get().getId());
        responseData.put("userName", storedUser.get().getUserName());
        responseData.put("emailId", storedUser.get().getEmailId());
        responseData.put("authority", storedUser.get().getAuthority());
        responseData.put("createdAt", storedUser.get().getCreatedAt());
        Map<String, String> tokens = jwtService.generateTokens(storedUser.get());
        responseData.put("accessToken", tokens.get("accessToken"));
        responseData.put("refreshToken", tokens.get("refreshToken"));
        responseData.put("updatedAt", storedUser.get().getUpdatedAt());
        return responseData;
    }

    public User retrieveById(final String id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException(Constant.NOT_FOUND));
    }

    public List<User> retrieveAll() {
        return this.userRepository.findAll();
    }

    public String deleteById(final String id) {
        final User user = this.userRepository.findById(id).orElseThrow(() -> new UserNameNotFoundException("User not found for this id : " + id));
        this.userRepository.delete(user);
        return Constant.REMOVE;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailId(email);
    }

    public Map<String, String> refreshToken(final String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BadRequestServiceAlertException("Refresh token is missing or malformed");
        }
        String email = jwtService.extractUserName(refreshToken);
        if (email == null) {
            throw new BadRequestServiceAlertException("Token must can contain an email.");
        }
        User user = findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!jwtService.validateToken(refreshToken, user)) {
            throw new UnAuthorizedException("Token has expired or is invalid");
        }
        String accessToken = jwtService.generateToken(user, false);
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("accessToken", accessToken);
        tokenData.put("refreshToken", refreshToken);
        return tokenData;
    }
}