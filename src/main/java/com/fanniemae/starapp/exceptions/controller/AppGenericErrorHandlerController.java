package com.fanniemae.starapp.exceptions.controller;

import com.fanniemae.starapp.commons.MessageConstants;
import com.fanniemae.starapp.exceptions.AppErrorResponse;
import com.fanniemae.starapp.exceptions.throwables.AppRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class AppGenericErrorHandlerController {

    private static final Logger LOGGER = LogManager.getLogger(AppGenericErrorHandlerController.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleDefaultException(Exception ext){
        LOGGER.error("###### Handling Default Exception! ######");
        ext.printStackTrace();
        final AppErrorResponse response = new AppErrorResponse();
        response.setMessage(MessageConstants.DEFAULT_MESSAGE);
        response.setErrorCode(MessageConstants.DEFAULT_ERROR_CODE);

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(AppRuntimeException.class)
    public final ResponseEntity<Object> handleApplicationException(AppRuntimeException ex){
        LOGGER.error("###### Handling Application Exception! ######");

        final AppErrorResponse response = new AppErrorResponse();
        response.setMessage(ex.getMessage());
        response.setErrorCode(ex.getMessageCode());

        HttpStatus status;
        switch (ex.getErrorType()){
            case ACCESS_ERROR:
                status = HttpStatus.UNAUTHORIZED;
                break;
            case REQUEST_ERROR:
                status = HttpStatus.BAD_REQUEST;
                break;
            case PROVIDER_ERROR:
                status = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            case APPLICATION_ERROR:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;

            default:
                LOGGER.error("Defaulting to system error!ØØ");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }




}
