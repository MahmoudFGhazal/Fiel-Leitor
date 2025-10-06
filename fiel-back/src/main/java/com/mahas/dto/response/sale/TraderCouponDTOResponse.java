package com.mahas.dto.response.sale;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraderCouponDTOResponse implements DTOResponse {
    private Integer id;
    private Double value;
    private Boolean used;
    private SaleDTOResponse sale;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof TraderCoupon t) {
            this.id = t.getId();
            this.value = t.getValue();
            this.used = t.getUsed();
            
            if (t.getSale() != null) {
                this.sale = new SaleDTOResponse();
                this.sale.mapFromEntity(t.getSale());
            }
        }
    }
}