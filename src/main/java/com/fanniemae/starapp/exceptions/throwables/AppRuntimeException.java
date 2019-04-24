package com.fanniemae.starapp.exceptions.throwables;

import com.fanniemae.starapp.commons.AppErrorType;

public class AppRuntimeException extends RuntimeException {

    private AppErrorType errorType;
    private String messageCode;
    private String message;

    public AppRuntimeException(String message, String messageCode, AppErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        this.message = message;
        this.messageCode = messageCode;
    }


    public AppRuntimeException(String message, String messageCode, AppErrorType errorType) {
        super(message);
        this.errorType = errorType;
        this.message = message;
        this.messageCode = messageCode;
    }


    public String getMessageCode() {
        return this.messageCode;
    }

    public String getMessage() {
        return this.message;
    }

    public AppErrorType getErrorType() {
        return this.errorType;
    }


}
