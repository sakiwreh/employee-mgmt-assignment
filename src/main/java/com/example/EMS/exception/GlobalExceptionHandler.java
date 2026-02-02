package com.example.EMS.exception;

import com.example.EMS.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e){
        StringBuilder errorMessages = new StringBuilder();

        e.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessages.append(fieldName).append(": ").append(message).append(";");
        });

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST,errorMessages.toString());
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e.getMessage().toLowerCase().contains("not found") || e.getMessage().toLowerCase().contains("not present")){
            status = HttpStatus.NOT_FOUND;
        }

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(status,e.getMessage());
        return new ResponseEntity<>(errorResponseDto,status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e){
        String message = e.getMessage();
        if(message==null){
            message="Server Error";
        }
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR,message);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


}
