package com.fanniemae.starapp.controllers.appinfo;


import com.fanniemae.starapp.domains.AppInfo;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/app-info")
public class AppInfoController {

    private static final Logger LOGGER = LogManager.getLogger(AppInfoController.class);

    @Autowired
    Trello trelloApi;

    @Value("${starapp.trello.receivedboardid}")
    String receivedBoardId;

    @GetMapping("/status")
    public AppInfo getAppInfoStatus(){

        LOGGER.info("Getting the status of App!");
        AppInfo info = new AppInfo();
        info.setName("Star Trooper");
        info.setStatus("Running!");
        info.setType("Demo");

        return info;
    }

    @GetMapping("/cards")
    public List<Card> getCardsforBoard(@RequestParam String boardId) {
        Argument args = new Argument(boardId, boardId);
        return trelloApi.getListCards(boardId, args);

    }

    @PostMapping("/card")
    public Card createCardforBoard(@RequestBody Card card) {
        return trelloApi.createCard(receivedBoardId, card);

    }

}
