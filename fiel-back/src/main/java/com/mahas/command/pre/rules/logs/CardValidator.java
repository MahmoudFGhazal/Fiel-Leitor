package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.domain.user.Card;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.CardDTORequest;

@Component
public class CardValidator {
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
}
