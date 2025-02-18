package com.showroommanagement_jwt.exception;

import com.showroommanagement_jwt.dto.ResponseDTO;
import com.showroommanagement_jwt.util.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestServiceAlertException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestServiceAlertException(final BadRequestServiceAlertException exception, WebRequest webRequest) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", Constant.DATA_NULL);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDTO> handleUnauthorizedException(final AuthorizationDeniedException exception) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), Constant.UNAUTHORIZED, exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUsernameNotFoundException(final UserNameNotFoundException exception) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_FOUND.value(), "ID Does not Exits", null);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseDTO> handleNoHandlerFoundException(final NoResourceFoundException exception) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_FOUND.value(), "Endpoint not found", exception.getResourcePath());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDTO> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed", exception.getMethod());
        return new ResponseEntity<>(responseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResourceNotFoundException(final ResourceNotFoundException exception) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.NOT_FOUND.value(), "Requested Resource Not Found", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JWTSignatureException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final JWTSignatureException exception, WebRequest webRequest) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final SignatureException exception, WebRequest webRequest) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final ExpiredJwtException exception, WebRequest webRequest) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final UnsupportedJwtException exception, WebRequest webRequest) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ResponseDTO> handleSignatureException(final MalformedJwtException exception, WebRequest webRequest) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception exception) {
        exception.printStackTrace();
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
