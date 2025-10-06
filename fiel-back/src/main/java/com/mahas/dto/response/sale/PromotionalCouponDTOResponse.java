package com.mahas.dto.response.sale;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionalCouponDTOResponse implements DTOResponse {
    private Integer id;
    private Double value;
    private Boolean used;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof PromotionalCoupon p) {
            this.id = p.getId();
            this.value = p.getValue();
            this.used = p.getUsed();
        }
    }
}