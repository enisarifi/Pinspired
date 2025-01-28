package com.example.pinspired.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private String  method;
}
