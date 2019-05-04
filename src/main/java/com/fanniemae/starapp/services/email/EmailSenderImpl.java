package com.fanniemae.starapp.services.email;

import com.fanniemae.starapp.controllers.request.ContactUsBean;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailSenderImpl implements EmailSender {
    @Value("${starapp.sendgrid.key}")
    private String sendGridKey;

    @Value("${starapp.email.from}")
    private String mailFrom;

    private static SendGrid sendGrid;

    @Override
    public SendGrid getSendGrid() {
        if (sendGrid == null) {
            sendGrid = new SendGrid(sendGridKey);
        }
        return sendGrid;
    }

    @Override
    public boolean send(ContactUsBean contactUsBean) {
        getSendGrid();
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(getMail(contactUsBean).build());

            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private Mail getMail(ContactUsBean contactUsBean) {
        Email from = new Email(mailFrom);
        String subject = "Hello " + contactUsBean.getFirstName() + " " + contactUsBean.getLastName();
        Email to = new Email(contactUsBean.getEmail());
        Content content = new Content("text/plain", contactUsBean.getMessage());

        return new Mail(from, subject, to, content);
    }
}
