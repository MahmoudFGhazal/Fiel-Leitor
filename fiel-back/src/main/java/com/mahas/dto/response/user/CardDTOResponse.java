package com.mahas.dto.response.user;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.Card;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTOResponse implements DTOResponse {
    private Integer id;
    private UserDTOResponse user;
    private String holder;
    private String bin;
    private String last4;
    private String brand;
    private String expMonth;
    private String expYear;
    private Boolean principal;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Card c) {
            this.id = c.getId();
            this.holder = c.getHolder();
            this.bin = c.getBin();
            this.last4 = c.getLast4();
            this.brand = c.getBrand();
            this.principal = c.getPrincipal();
            this.expMonth = c.getExpMonth();
            this.expYear = c.getExpYear();
            
            if (c.getUser() != null) {
                this.user = new UserDTOResponse();
                this.user.mapFromEntity(c.getUser());
            }
        }
    }
}
