package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.controllers.request.ContactUsBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic API that user can send a message and the APP will respond in the email
 */
@RestController
@RequestMapping("/communication")
public class ContactUsController {

    private static final Logger LOGGER = LogManager.getLogger(ContactUsController.class);

    /**
     * This API will capture user information and create a response back to the user's provided email
     *
     * @param message
     * @return
     */
    @PostMapping("/message")
    public ResponseEntity sendMessage(@RequestBody ContactUsBean message) {

        //TODO: Delegate to the service layer that will perform the following:
        // 1. Create a Trello Card
        // 2. Respond back to the requester's email using SendGrid implementation.
        // 3. Store the user information as email channel for future communication such as when a trello card gets updated.

        return new ResponseEntity(HttpStatus.OK);
    }

}
