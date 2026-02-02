package com.example.EMS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorResponseDto {

    private int statusCode;
    private String message;

    public ErrorResponseDto(HttpStatus status, String message) {
        statusCode = status.value();
        this.message = message;
    }
}
