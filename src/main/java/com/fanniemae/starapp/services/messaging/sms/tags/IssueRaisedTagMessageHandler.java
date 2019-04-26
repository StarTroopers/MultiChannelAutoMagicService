package com.fanniemae.starapp.services.messaging.sms.tags;

import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;

/**
 * Handler when message starts with #CreateIssue
 */
public class IssueRaisedTagMessageHandler implements TagMessagerHandler{


    @Override
    public MessageResponse handleMessage(String message, String traceId) {


        return null;
    }
}
