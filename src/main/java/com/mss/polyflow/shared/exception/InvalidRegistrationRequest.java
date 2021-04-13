package com.mss.polyflow.shared.exception;

public class InvalidRegistrationRequest extends RuntimeException{


    /**
     * TODO - way to find the name of this class programmatically by reflection or some way in string format
     */
    private static final String name = "InvalidRegistrationRequest";

    private static final String exceptionMessage = "Invalid Registration request, please check the request";
    public InvalidRegistrationRequest() {
        super(exceptionMessage);
    }

    public String getName() {
        return this.name;
    }

}
