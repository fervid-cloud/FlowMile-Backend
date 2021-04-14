package com.mss.polyflow.shared.exception;


import com.mss.polyflow.shared.exception.ExceptionResponseModel.ExceptionResponseModelBuilder;
import com.mss.polyflow.shared.utilities.response.ResponseModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public final ResponseEntity<Object> handleMiscellaneousException(MiscellaneousException exception) {
        return sendResponse(erb -> erb
                                       .message(exception.getMessage())
                                       .error(exception.getName())
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
