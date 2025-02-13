package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.UserCredential;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.exception.ResourceNotFoundException;
import com.showroommanagement_jwt.exception.UnAuthorizedException;
import com.showroommanagement_jwt.repository.UserCredentialRepository;
import com.showroommanagement_jwt.util.Authority;
import com.showroommanagement_jwt.util.Constant;
import com.showroommanagement_jwt.util.UserCredentialValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserCredentialService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    //    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserCredentialValidation userCredentialValidation;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Transactional
    public ResponseDTO createSalesManager(final UserCredential userCredential) {
        userCredential.setAuthority(Authority.ROLE_SALES_MANAGER);
        return createUser(userCredential, "Sales Manager Created Successfully");
    }

    @Transactional
    public ResponseDTO createAdmin(final UserCredential userCredential) {
        userCredential.setAuthority(Authority.ROLE_ADMIN);
        return createUser(userCredential, "Admin Created Successfully");
    }

    @Transactional
    public ResponseDTO createSalesman(final UserCredential userCredential) {
        userCredential.setAuthority(Authority.ROLE_SALESMAN);
        return createUser(userCredential, "Salesman Created Successfully");
    }

    @Transactional
    public ResponseDTO createCustomer(final UserCredential userCredential) {
        userCredential.setAuthority(Authority.ROLE_CUSTOMER);
        return createUser(userCredential, "Salesman Created Successfully");
    }

    public ResponseDTO createUser(final UserCredential user, String message) {
        if (!userCredentialValidation.isValidEmail(user.getEmailId())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        if (!userCredentialValidation.isValidPassword(user.getPassword())) {
            throw new BadRequestServiceAlertException("Invalid Password format");
        }
        if (userCredentialRepository.existsByEmailId(user.getEmailId())) {
            throw new BadRequestServiceAlertException("Account Already exists");
        }
        user.setAuthority(user.getAuthority());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userCredentialRepository.save(user);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", user.getId());
        responseData.put("userName", user.getUserName());
        responseData.put("emailId", user.getEmailId());
        responseData.put("authority", user.getAuthority());
        responseData.put("createdAt", user.getCreatedAt());
        responseData.put("updatedAt", user.getUpdatedAt());
        return new ResponseDTO(HttpStatus.OK.value(), message, responseData);
    }

    public ResponseDTO verifyUser(final UserCredential user) {
        if (!userCredentialValidation.isValidEmail(user.getEmailId()))
            throw new BadRequestServiceAlertException("Incorrect EmailId.");
        if (!userCredentialValidation.isValidPassword(user.getPassword()))
            throw new BadRequestServiceAlertException("Incorrect Password.");
        Optional<UserCredential> storedUser = userCredentialRepository.findByEmailId(user.getEmailId());
        if (storedUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found for email:" + user.getEmailId());
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), storedUser.get().getPassword())) {
            return new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Invalid User", Constant.UNAUTHORIZED);
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
        return new ResponseDTO(HttpStatus.OK.value(), "Login Successful", responseData);
    }

    public ResponseDTO retrieveById(final String id) {
        if (this.userCredentialRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.userCredentialRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.userCredentialRepository.findAll());
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.userCredentialRepository.existsById(id)) {
            this.userCredentialRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, Constant.DELETE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public Optional<UserCredential> findByEmail(String email) {
        return userCredentialRepository.findByEmailId(email);
    }

    public ResponseDTO refreshToken(final String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BadRequestServiceAlertException("Refresh token is missing or malformed");
        }
        String email = jwtService.extractUserName(refreshToken);
        if (email == null) {
            throw new BadRequestServiceAlertException("Token must can contain an email.");
        }
        UserCredential user = findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!jwtService.validateToken(refreshToken, user)) {
            throw new UnAuthorizedException("Token has expired or is invalid");
        }
        String accessToken = jwtService.generateToken(user, false);
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("accessToken", accessToken);
        tokenData.put("refreshToken", refreshToken);
        return new ResponseDTO(HttpStatus.OK.value(), "New Token Generated", tokenData);
    }
}