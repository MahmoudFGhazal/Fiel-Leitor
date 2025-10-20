package com.mahas.dto.response.sale;

import java.math.BigDecimal;

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
    private SaleDTOResponse originSale;
    private SaleDTOResponse appliedSale;
    private String code;
    private BigDecimal value;
    private Boolean used;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof TraderCoupon t) {
            this.id = t.getId();
            this.code = t.getCode();
            this.value = t.getValue();
            this.used = t.getUsed();
            
            if (t.getAppliedSale() != null) {
                this.appliedSale = new SaleDTOResponse();
                this.appliedSale.mapFromEntity(t.getAppliedSale());
            }

            if (t.getOriginSale() != null) {
                this.originSale = new SaleDTOResponse();
                this.originSale.mapFromEntity(t.getOriginSale());
            }
        }
    }
}