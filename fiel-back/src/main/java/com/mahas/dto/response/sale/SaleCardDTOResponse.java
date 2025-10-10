package com.mahas.dto.response.sale;

import java.math.BigDecimal;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.sale.SaleCard;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.user.CardDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCardDTOResponse implements DTOResponse {
    private SaleDTOResponse sale;
    private CardDTOResponse card;
    private BigDecimal percent;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof SaleCard sc) {
            if (sc.getSale() != null) {
                this.sale = new SaleDTOResponse();
                this.sale.mapFromEntity(sc.getSale());
            }

            if (sc.getCard() != null) {
                this.card = new CardDTOResponse();
                this.card.mapFromEntity(sc.getCard());
            }

            this.percent = sc.getPercent();
        }
    }
}