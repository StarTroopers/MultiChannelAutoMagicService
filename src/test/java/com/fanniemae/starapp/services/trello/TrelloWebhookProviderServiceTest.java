package com.fanniemae.starapp.services.trello;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TrelloWebhookProviderServiceTest {


    @Test
    @Ignore
    public void testWeb(){
        final TrelloWebhookProviderService service = new TrelloWebhookProviderService();
        service.invokeWebHook();
    }
}
