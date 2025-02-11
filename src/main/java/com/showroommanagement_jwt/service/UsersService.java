package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Users;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.exception.ResourceNotFoundException;
import com.showroommanagement_jwt.exception.UnAuthorizedException;
import com.showroommanagement_jwt.repository.UsersRepository;
import com.showroommanagement_jwt.util.Authority;
import com.showroommanagement_jwt.util.Constant;
import com.showroommanagement_jwt.util.UsersValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    //    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UsersValidation usersValidation;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Transactional
    public ResponseDTO createSalesManager(final Users users) {
        users.setAuthority(Authority.ROLE_SALES_MANAGER);
        return createUser(users, "Sales Manager Created Successfully");
    }

    @Transactional
    public ResponseDTO createAdmin(final Users users) {
        users.setAuthority(Authority.ROLE_ADMIN);
        return createUser(users, "Admin Created Successfully");
    }

    @Transactional
    public ResponseDTO createSalesman(final Users users) {
        users.setAuthority(Authority.ROLE_SALESMAN);
        return createUser(users, "Salesman Created Successfully");
    }

    @Transactional
    public ResponseDTO createCustomer(final Users users) {
        users.setAuthority(Authority.ROLE_CUSTOMER);
        return createUser(users, "Salesman Created Successfully");
    }

    public ResponseDTO createUser(final Users user, String message) {
        if (!usersValidation.isValidEmail(user.getEmailId())) {
            throw new BadRequestServiceAlertException("Invalid Email format");
        }
        if (!usersValidation.isValidPassword(user.getPassword())) {
            throw new BadRequestServiceAlertException("Invalid Password format");
        }
        if (usersRepository.existsByEmailId(user.getEmailId())) {
            throw new BadRequestServiceAlertException("Account Already exists");
        }
        user.setAuthority(user.getAuthority());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", user.getId());
        responseData.put("userName", user.getUserName());
        responseData.put("emailId", user.getEmailId());
        responseData.put("authority", user.getAuthority());
        responseData.put("createdAt", user.getCreatedAt());
        responseData.put("updatedAt", user.getUpdatedAt());
        return new ResponseDTO(HttpStatus.OK.value(), message, responseData);
    }

    public ResponseDTO verifyUser(final Users user) {
        if (!usersValidation.isValidEmail(user.getEmailId()))
            throw new BadRequestServiceAlertException("Incorrect EmailId.");
        if (!usersValidation.isValidPassword(user.getPassword()))
            throw new BadRequestServiceAlertException("Incorrect Password.");
        Optional<Users> storedUser = usersRepository.findByEmailId(user.getEmailId());
        if (storedUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found for email:" + user.getEmailId());
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), storedUser.get().getPassword())) {
            throw new UnAuthorizedException("Invalid login credentials.");
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
        if (this.usersRepository.existsById(id)) {
            return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.usersRepository.findById(id));
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.usersRepository.findAll());
    }

    public ResponseDTO deleteById(final String id) {
        if (id == null) {
            throw new BadRequestServiceAlertException(Constant.DATA_NULL);
        }
        if (this.usersRepository.existsById(id)) {
            this.usersRepository.deleteById(id);
            return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, Constant.DELETE);
        } else {
            throw new BadRequestServiceAlertException(Constant.ID_DOES_NOT_EXIST);
        }
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmailId(email);
    }

    public ResponseDTO refreshToken(final String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BadRequestServiceAlertException("Refresh token is missing or malformed");
        }
        String email = jwtService.extractUserName(refreshToken);
        if (email == null) {
            throw new BadRequestServiceAlertException("Token must can contain an email.");
        }
        Users user = findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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