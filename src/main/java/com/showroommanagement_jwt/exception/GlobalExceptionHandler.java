package com.showroommanagement_jwt.exception;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.service.UsersService;
import com.showroommanagement_jwt.util.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestServiceAlertException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestServiceAlertException(final BadRequestServiceAlertException exception, WebRequest webRequest) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setStatusCode(400);
        responseDTO.setData(Constant.NOT_FOUND);
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleSecurityException(Exception exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setStatusCode(400);
        responseDTO.setData(Constant.NOT_FOUND);
        return ResponseEntity.badRequest().body(responseDTO);
    }
}
