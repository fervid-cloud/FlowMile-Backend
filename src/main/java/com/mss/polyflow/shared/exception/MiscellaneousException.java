package com.mss.polyflow.shared.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class MiscellaneousException extends RuntimeException{

    private static final String name = "Miscellaneous Exception";

    public MiscellaneousException(String message) {
        super(message);
    }

}
