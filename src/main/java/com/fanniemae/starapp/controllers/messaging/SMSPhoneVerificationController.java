package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.commons.AppErrorType;
import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.commons.MessageConstants;
import com.fanniemae.starapp.controllers.BaseAppController;
import com.fanniemae.starapp.controllers.request.PhoneVerifyBean;
import com.fanniemae.starapp.controllers.request.PhoneVerifyCheckBean;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import com.fanniemae.starapp.services.messaging.sms.TwilioSMSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms-admin")
public class SMSPhoneVerificationController  extends BaseAppController {

    private static final Logger LOGGER = LogManager.getLogger(SMSPhoneVerificationController.class);

    @Autowired
    private TwilioSMSService smsService;

    /**
     * Verify the phone number provided!
     *
     * @param phoneInfo
     * @param traceId
     */
    @PostMapping("/verify")
    public void verifyNewUserPhone(@RequestBody PhoneVerifyBean phoneInfo,
                                   @RequestHeader(name = AppHttpHeaders.TRACEID_HEADER, required = false) String traceId) {
        LOGGER.info("Verifying a new phone number of {}. traceId of {}", phoneInfo.getPhoneNumber(), traceId);

        if(phoneInfo == null || phoneInfo.getPhoneNumber() == null){
            errorHandler.throwAppException(MessageConstants.CODE_INCOMPLETE_REQUEST_ERROR, AppErrorType.REQUEST_ERROR, null);
        }

        final SMSMessage message = new SMSMessageRequest();
        message.setTo(phoneInfo.getPhoneNumber());

        //TODO: Specify a custom message during verification. null for now
        message.setCustomMessage(null);

        final MessageResponse response = smsService.initUserPhoneVerification(message, traceId);
        if(!response.isStatus()){
            LOGGER.error("Error found verifying the phone of {}. traceId of {}", phoneInfo.getPhoneNumber(), traceId);
            errorHandler.throwAppException(MessageConstants.CODE_EXECUTION_ERROR, AppErrorType.APPLICATION_ERROR, null);
        }

    }


    @PostMapping("/confirm")
    public void confirmNewUserPhone(@RequestBody PhoneVerifyCheckBean phoneInfo,
                                    @RequestHeader(name = AppHttpHeaders.TRACEID_HEADER, required = false) String traceId) {
        LOGGER.info("Confirming the new phone number of {} with code provided. traceId of {}", phoneInfo.getPhoneNumber(), traceId);
        if(phoneInfo == null || phoneInfo.getPhoneNumber() == null || phoneInfo.getVerificationCode() == null){
            errorHandler.throwAppException(MessageConstants.CODE_INCOMPLETE_REQUEST_ERROR, AppErrorType.REQUEST_ERROR, null);
        }
        final SMSMessage message = new SMSMessageRequest();
        message.setTo(phoneInfo.getPhoneNumber());
        message.setVerifyCode(phoneInfo.getVerificationCode());

        final MessageResponse response = smsService.confirmUserPhoneVerification(message, traceId);
        if(!response.isStatus()){
            LOGGER.error("Error found confirming the phone of {}. traceId of {}", phoneInfo.getPhoneNumber(), traceId);
            errorHandler.throwAppException(MessageConstants.CODE_EXECUTION_ERROR, AppErrorType.APPLICATION_ERROR, null);
        }
    }


}
