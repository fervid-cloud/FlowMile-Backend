package com.mss.polyflow.shared.exception;


import com.mss.polyflow.shared.exception.ExceptionResponseModel.ExceptionResponseModelBuilder;
import com.mss.polyflow.shared.utilities.response.ResponseModel;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalServletExceptionHandler {


    private final DateTimeFormatter currentFormatForException;

    public GlobalServletExceptionHandler() {
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

    @ExceptionHandler({MiscellaneousException.class})
    public final ResponseEntity<Object> handleMiscellaneousExceptionHandler(MiscellaneousException exception) {
        return sendResponse(erb -> erb
                                       .message(exception.getMessage())
                                       .error(exception.getName())
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public final ResponseEntity<Object> handleUserNotFoundExceptionHandler(UserNotFoundException exception) {
        return sendResponse(erb -> erb
                                       .message(exception.getMessage())
                                       .error(exception.getName())
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public final ResponseEntity<Object> SQLIntegrityConstraintViolationExceptionHandler(DataIntegrityViolationException exception) {
/*
        Since you are using spring-data JPA repository to save entity you should catch DataIntegrityViolationException instead of SQLException
        The problem with showing user-friendly messages in the case of constraint violation is that the constraint name
        is lost when Hibernate's ConstraintViolationException is being translated into Spring's DataIntegrityViolationException.*/
        return sendResponse(erb -> erb
                                       .message("Either duplicate or null entry found")
                                       .error("DataIntegrityViolationException")
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.BAD_GATEWAY));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public final ResponseEntity<Object> BadCredentialsExceptionHandler(BadCredentialsException exception) {
        return sendResponse(erb -> erb
                                       .message(exception.getMessage())
                                       .error("BadCredentialsException")
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.UNAUTHORIZED));
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.info("Invalid data is there -------------------- {}", errors);
        return sendResponse(erb -> erb
                                       .message("Invalid data provided")
                                       .error(errors.toString())
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler({BindException.class})
    public final ResponseEntity<Object> QueryParametersFieldsValidationExceptionHandler(BindException exception) {
        Map<String, String> errors = new HashMap<>();
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.info("Invalid data is there -------------------- {}", errors);
        return sendResponse(erb -> erb
                                       .message("Invalid query search provided")
                                       .error(errors.toString())
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<Object> NotFoundExceptionHandler(NotFoundException exception) {
        return sendResponse(erb -> erb
                                       .message(exception.getMessage())
                                       .error("Not Found Exception")
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleGenericExceptionHandler(Exception exception) {
        exception.printStackTrace();
        return sendResponse(erb -> erb
                                       .message(exception.getMessage())
                                       .error("Generic Exception")
                                       .time(getCurrentLocalTime())
                                       .httpStatus(HttpStatus.BAD_REQUEST));
    }


    //////////////////////////////// UTILITY METHODS

    private ResponseEntity<Object> sendResponse(ExceptionResponseModel exceptionResponseModel) {
        return new ResponseEntity<>(exceptionResponseModel, exceptionResponseModel.getHttpStatus());
    }        //////////////////////////////// UTILITY METHODS

    private ResponseEntity<Object> sendResponse(Consumer<ExceptionResponseModelBuilder> consumer) {
        ExceptionResponseModelBuilder exceptionResponseModelBuilder = ExceptionResponseModel.builder();
        consumer.accept(exceptionResponseModelBuilder);
        ExceptionResponseModel exceptionResponseModel = exceptionResponseModelBuilder.build();
        return new ResponseEntity<>(exceptionResponseModel, exceptionResponseModel.getHttpStatus());
    }


    private final String getCurrentLocalTime() {
        return LocalDateTime.now().format(currentFormatForException);
    }

}
