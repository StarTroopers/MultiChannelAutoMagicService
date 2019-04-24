package com.fanniemae.starapp.exceptions;

public class AppErrorResponse {


    private String message;
    private String errorCode;

    public AppErrorResponse(String errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public AppErrorResponse(){}


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}

