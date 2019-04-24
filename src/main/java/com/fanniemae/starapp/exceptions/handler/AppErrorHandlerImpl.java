package com.fanniemae.starapp.exceptions.handler;

import com.fanniemae.starapp.commons.AppErrorType;
import com.fanniemae.starapp.commons.MessageConstants;
import com.fanniemae.starapp.exceptions.throwables.AppRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Component
public class AppErrorHandlerImpl implements AppErrorHandler {

    private static final Logger LOGGER = LogManager.getLogger(AppErrorHandlerImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getErrorMessage(String errorCode) {

        String errMessage = null;

        try {
            errMessage = messageSource.getMessage(errorCode, null, Locale.US);
            if (errMessage == null || errMessage.isEmpty()) {
                errMessage = messageSource.getMessage(MessageConstants.DEFAULT_ERROR_KEY, null, Locale.US);
            }

        } catch (Exception ex) {
            LOGGER.warn("Could not get the error message for this code {}", errorCode);
            errMessage = "Unknown Error Message - 9000";
        }

        return errMessage;
    }

    @Override
    public void throwAppException(String errorCode, AppErrorType errorType, Throwable cause) {

        String message = getErrorMessage(errorCode);
        if (message == null || StringUtils.isEmpty(message)) {
            message = "Error found processing the request! Please contact the admin!";
        }


        if (cause != null) {
                throw new AppRuntimeException(message, errorCode, errorType, cause);

            } else {
                throw new AppRuntimeException(message, errorCode, errorType);
            }


    }
}
