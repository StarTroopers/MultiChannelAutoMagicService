package com.fanniemae.starapp.controllers.request;

/**
 * Request Object that is exposed in the controllers. This will have all the
 * Swagger information needed.
 */
public class SMSMessageBean {

    private String toPhoneNumber;
    private String message;

    public String getToPhoneNumber() {
        return toPhoneNumber;
    }

    public void setToPhoneNumber(String toPhoneNumber) {
        this.toPhoneNumber = toPhoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
