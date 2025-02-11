package com.showroommanagement_jwt.exception;

import com.showroommanagement_jwt.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestServiceAlertException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestServiceAlertException(final BadRequestServiceAlertException exception, WebRequest webRequest) {
        exception.printStackTrace();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", exception.getMessage());
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", responseData);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ResponseDTO> handleUnauthorizedException(final UnAuthorizedException exception) {
        exception.printStackTrace();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", exception.getMessage());
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", responseData);
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(final ResourceNotFoundException exception) {
        exception.printStackTrace();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", exception.getMessage());
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_FOUND.value(), "Resource Not Found", responseData);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final SignatureException exception){
        exception.printStackTrace();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", exception.getMessage());
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT signature", responseData);
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception exception) {
        exception.printStackTrace();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", "An unexpected error occurred : " + exception.getMessage());
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", responseData);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
