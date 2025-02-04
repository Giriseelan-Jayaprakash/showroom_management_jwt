package com.showroommanagement_jwt.service;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.entity.Users;
import com.showroommanagement_jwt.exception.BadRequestServiceAlertException;
import com.showroommanagement_jwt.repository.UsersRepository;
import com.showroommanagement_jwt.util.UsersValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UsersValidation usersValidation;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public ResponseDTO createUser(final Users user) {
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
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatusCode(HttpStatus.CREATED.value());
//        responseDTO.setMessage("Created Successfully");
        responseDTO.setData(responseData);
        return responseDTO;
    }

    public ResponseDTO verifyUser(final Users user) {
        if (!usersValidation.isValidEmail(user.getEmailId()))
            throw new BadRequestServiceAlertException("Incorrect EmailId");
        if (!usersValidation.isValidPassword(user.getPassword()))
            throw new BadRequestServiceAlertException("Incorrect Password");
        Optional<Users> storedUser = usersRepository.findByEmailId(user.getEmailId());
        ResponseDTO responseDTO = new ResponseDTO();
        if (storedUser.isEmpty()) {
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setMessage("User not found");
            responseDTO.setData(null);
            return responseDTO;
        }
        if (bCryptPasswordEncoder.matches(user.getPassword(), storedUser.get().getPassword())) {
            if (user == null && user.getId() == null) {
                throw new BadRequestServiceAlertException("User or User ID is null. Cannot generate token.");
            }
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", storedUser.get().getId());
            responseData.put("userName", storedUser.get().getUserName());
            responseData.put("emailId", storedUser.get().getEmailId());
            responseData.put("authority", storedUser.get().getAuthority());
            responseData.put("createdAt", storedUser.get().getCreatedAt());
            responseData.put("UpdatedAt", storedUser.get().getUpdatedAt());
            responseData.put("token", jwtService.generateToken(storedUser.get()));
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setMessage("Login Successful");
            responseDTO.setData(responseData);
            return responseDTO;
        } else {
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            responseDTO.setMessage("Invalid login credentials");
            responseDTO.setData(null);
            return responseDTO;
        }
    }
}