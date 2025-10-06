package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.dto.request.sale.PromotionalCouponDTORequest;

@Component
public class PromotionalCouponValidator {
    public PromotionalCoupon toEntity(PromotionalCouponDTORequest dto) {
        if (dto == null) return null;

        PromotionalCoupon pc = new PromotionalCoupon();
        pc.setId(dto.getId() != null ? dto.getId().intValue() : null);
        pc.setValue(dto.getValue());
        pc.setUsed(dto.getUsed());

        return pc;
    }
}
