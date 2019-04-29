package com.fanniemae.starapp.controllers.appinfo;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fanniemae.starapp.domains.AppInfo;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.Card;
import com.twilio.http.Response;

@RestController
@RequestMapping("/app-info")
public class AppInfoController {

    private static final Logger LOGGER = LogManager.getLogger(AppInfoController.class);

    @Autowired
    Trello trelloApi;

    @Value("${starapp.trello.idlist}")
    String idlist;

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
        return trelloApi.createCard(idlist, card);
    }

    @PutMapping("/card")
    public ResponseEntity<Card> updateCardforBoard(@RequestParam String idCard, @RequestParam String comment) {
        trelloApi.addCommentToCard(idCard, comment);
        Card card = new Card();
        card.setId(idCard);
        card.setDesc("Comments added to the card");
        return new ResponseEntity<>(card, HttpStatus.OK);
    }
}
