package com.mahas.dto.response.product;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.PriceGroup;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceGroupDTOResponse implements DTOResponse {
    
    private Integer id;
    private String name;
    private Double marginPct;
    private Boolean active;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof PriceGroup pg) {
            this.id = pg.getId();
            this.name = pg.getName();
            this.marginPct = pg.getMarginPct();
            this.active = pg.getActive();
        }
    }
}