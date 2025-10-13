package com.mahas.command.pre.rules.logs;

import java.math.BigDecimal;

import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.SaleCard;
import com.mahas.domain.user.Card;
import com.mahas.dto.request.sale.SaleCardDTORequest;
import com.mahas.exception.ValidationException;

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

    public void checkPercent(SaleCardDTORequest[] saleCards) {
        BigDecimal sum = BigDecimal.ZERO;

        for(SaleCardDTORequest saleCard : saleCards) {
            BigDecimal percent = saleCard.getPercent();

            if(percent == null || percent.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException(saleCard.getCard().toString() + ": possui porcentagem invalida de " + percent);
            }

            sum = sum.add(percent);
        }

        if(sum.compareTo(BigDecimal.ONE) != 0) {
            throw new ValidationException("Soma das porcentagens do cartão não é igual a 100%");
        }
    }
}
