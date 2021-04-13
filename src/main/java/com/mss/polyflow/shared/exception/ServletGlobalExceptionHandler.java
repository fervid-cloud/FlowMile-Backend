package com.mss.polyflow.shared.exception;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServletGlobalExceptionHandler {


    private final DateTimeFormatter currentFormatForException;

    public ServletGlobalExceptionHandler() {
        this.currentFormatForException = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy, HH:mm:ss a");
    }


    @ExceptionHandler({InvalidRegistrationRequest.class})
    public final ResponseEntity<Object> handleInvalidRegistrationRequest(InvalidRegistrationRequest exception) {
        ExceptionResponseModel exceptionResponseModel = ExceptionResponseModel
                                                     .builder()
                                                     .message(exception.getMessage())
                                                     .error(exception.getName())
                                                     .time(getCurrentLocalTime())
                                                     .httpStatus(HttpStatus.BAD_REQUEST)
                                                     .build();

        return sendResponse(exceptionResponseModel);
    }


    //////////////////////////////// UTILITY METHODS

    private ResponseEntity<Object> sendResponse(ExceptionResponseModel exceptionResponseModel) {
        return new ResponseEntity<>(exceptionResponseModel, exceptionResponseModel.getHttpStatus());
    }

    private final String getCurrentLocalTime() {
        return LocalDateTime.now().format(currentFormatForException);
    }

}
