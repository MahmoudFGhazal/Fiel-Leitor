package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.user.BaseCardCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Card;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseCardCommand baseCardCommand;
   
    public Card toEntity(CardDTORequest dto) {
        if (dto == null) return null;

        Card card = new Card();
        card.setId(dto.getId() != null ? dto.getId().intValue() : null);
        card.setPrincipal(dto.getPrincipal());
        card.setBin(dto.getBin());
        card.setLast4(dto.getLast4());
        card.setHolder(dto.getHolder());
        card.setExpMonth(dto.getExpMonth());
        card.setExpYear(dto.getExpYear());
        card.setBrand(dto.getBrand());

        if (dto.getUser() != null) {
            User u = new User();
            u.setId(dto.getUser());
            card.setUser(u);
        }

        return card;
    }

    public boolean cardExists(Integer id) {
        CardDTORequest card = new CardDTORequest();
        card.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(card);
        request.setLimit(1);
        request.setPreCommand(baseCardCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }
}
