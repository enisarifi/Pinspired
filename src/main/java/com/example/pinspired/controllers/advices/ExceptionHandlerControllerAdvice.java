package com.example.pinspired.controllers.advices;

import com.example.pinspired.dtos.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    private final HttpSession httpSession;

    public ExceptionHandlerControllerAdvice(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(HttpServletRequest request,
//                                                                          EntityNotFoundException e) {
//        var errorResponse = new ErrorResponseDto();
//        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
//        errorResponse.setError(HttpStatus.NOT_FOUND.name());
//        errorResponse.setMessage(e.getMessage());
//        errorResponse.setPath(request.getRequestURI());
//        errorResponse.setMethod(request.getMethod());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//    }
//
//
//    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class,
//            IndexOutOfBoundsException.class,
//            MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
//    public ResponseEntity<ErrorResponseDto> handleRuntimeException(HttpServletRequest request,
//                                                                   Exception e) {
//        var errorResponse = new ErrorResponseDto();
//        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setError(HttpStatus.BAD_REQUEST.name());
//        errorResponse.setMessage(e.getMessage());
//        errorResponse.setPath(request.getRequestURI());
//        errorResponse.setMethod(request.getMethod());
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(HttpServletRequest request,
                                                            Exception e) {
        var errorResponse = new ErrorResponseDto();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setMethod(request.getMethod());

        if (e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof IllegalArgumentException || e instanceof IllegalStateException
                || e instanceof MethodArgumentNotValidException || e instanceof HandlerMethodValidationException) {
            status = HttpStatus.BAD_REQUEST;
        }

        errorResponse.setStatus(status.value());
        errorResponse.setError(status.name());

        return ResponseEntity.status(status).body(errorResponse);
    }

}
