package com.mahas.command.pre.rules.logs;

import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.SaleCard;
import com.mahas.domain.user.Card;
import com.mahas.dto.request.sale.SaleCardDTORequest;

import org.springframework.stereotype.Component;

@Component
public class SaleCardValidator {
    public SaleCard toEntity(SaleCardDTORequest dto) {
        if (dto == null) return null;

        SaleCard sc = new SaleCard();
        sc.setPercent(dto.getPercent());

        if (dto.getSale() != null) {
            Sale s = new Sale();
            s.setId(dto.getSale());
            sc.setSale(s);
        }

        if (dto.getCard() != null) {
            Card c = new Card();
            c.setId(dto.getCard());
            sc.setCard(c);
        }

        return sc;
    }
}
