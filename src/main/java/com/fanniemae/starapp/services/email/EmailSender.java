package com.fanniemae.starapp.services.email;

import com.fanniemae.starapp.controllers.request.ContactUsBean;
import com.sendgrid.SendGrid;

public interface EmailSender {
    SendGrid getSendGrid();
    boolean send(ContactUsBean contactUsBean);
}
