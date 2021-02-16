package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponseModel {

    private String error;

    private String message;

    private String time;

    private HttpStatus httpStatus;

}
