package com.example.oauth2ResourseServer.common.handle;

import com.example.oauth2ResourseServer.common.exception.RecordNotFountException;
import com.example.oauth2ResourseServer.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestController
@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(RecordNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse recordNotFound(Exception e) {
        return new ApiResponse(
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse usernameNotFound(Exception e) {
        return new ApiResponse(
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse badRequest(Exception e) {
        return new ApiResponse(
                e.getMessage(),
                null
        );
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse nullPointer(Exception e) {
        return new ApiResponse(
                e.getMessage(),
                null
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ApiResponse(
                errors.toString(),
                null
        );
    }

}
