package com.mahas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mahas.command.pre.base.user.BaseCardCommand;
import com.mahas.command.pre.rules.VerifyGetCardByUser;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.facade.IFacade;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/card")
public class CardController {
    @Autowired
    private IFacade facade;

    @Autowired
    private BaseCardCommand baseCardCommand;

    @Autowired
    private VerifyGetCardByUser verifyGetCardByUser;

    @GetMapping
    public ResponseEntity<FacadeResponse> getCard(
            @RequestParam(value = "cardId", required = true) Integer cardId
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseCardCommand);
        CardDTORequest card = new CardDTORequest();
        card.setId(cardId);
        request.setEntity(card); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<FacadeResponse> getCardByUser(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
        ) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(verifyGetCardByUser);
        request.setLimit(limit);
        request.setPage(page);
        CardDTORequest card = new CardDTORequest();
        card.setUser(userId);
        request.setEntity(card); 

        FacadeResponse response = facade.query(request);
        
        return ResponseEntity.ok(response);
    }

}
