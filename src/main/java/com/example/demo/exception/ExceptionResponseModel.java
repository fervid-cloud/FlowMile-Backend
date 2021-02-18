package com.example.demo.exception;

import java.util.function.Consumer;
import org.springframework.http.HttpStatus;

public class ExceptionResponseModel {

    private final String error;

    private final String message;

    private final String time;

    private final HttpStatus httpStatus;


    public ExceptionResponseModel(String error, String message, String time, HttpStatus httpStatus) {
        this.error = error;
        this.message = message;
        this.time = time;
        this.httpStatus = httpStatus;
    }

    /**
     * TODO find if can we make object of inner static class while keeping it private to the outer world
     * @return
     */
    public static ExceptionResponseModelBuilder builder() {
        return new ExceptionResponseModelBuilder();
    }


    /***
     * using builder patter using either lambda expression or anonymous class
     * @param exceptionResponseConsumerAction
     * @return
     */
    public static ExceptionResponseModel createResponse(
        Consumer<ExceptionResponseModelBuilder> exceptionResponseConsumerAction) {
        ExceptionResponseModelBuilder currentBuilder = builder();
        exceptionResponseConsumerAction.accept(currentBuilder);
        return currentBuilder.build();
    }


    /**
     * TODO explore more in depth of why we are able to access private member of passed object in the parameter
     * @param exceptionResponseModelBuilder
     */
    public ExceptionResponseModel(ExceptionResponseModelBuilder exceptionResponseModelBuilder) {
        this.error = exceptionResponseModelBuilder.error;
        this.message = exceptionResponseModelBuilder.message;
        this.time = exceptionResponseModelBuilder.time;
        this.httpStatus = exceptionResponseModelBuilder.httpStatus;
    }

    public static class ExceptionResponseModelBuilder {

        private String error;

        private String message;

        private String time;

        private HttpStatus httpStatus;

        public ExceptionResponseModelBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ExceptionResponseModelBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExceptionResponseModelBuilder time(String time) {
            this.time = time;
            return this;
        }

        public ExceptionResponseModelBuilder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ExceptionResponseModel build() {
            return new ExceptionResponseModel(this);
        }
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
