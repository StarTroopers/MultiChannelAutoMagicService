package com.fanniemae.starapp.exceptions.handler;

import com.fanniemae.starapp.commons.AppErrorType;

public interface AppErrorHandler {

    String getErrorMessage(String errorCode);

    void throwAppException(String messageCode, AppErrorType errorType, Throwable cause);
}
