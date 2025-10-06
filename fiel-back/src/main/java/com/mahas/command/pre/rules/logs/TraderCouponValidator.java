package com.mahas.command.pre.rules.logs;

import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.dto.request.sale.TraderCouponDTORequest;

import org.springframework.stereotype.Component;

@Component
public class TraderCouponValidator {
    public TraderCoupon toEntity(TraderCouponDTORequest dto) {
        if (dto == null) return null;

        TraderCoupon tc = new TraderCoupon();
        tc.setId(dto.getId() != null ? dto.getId() : null);
        tc.setValue(dto.getValue());
        tc.setUsed(dto.getUsed());

        if (dto.getSale() != null) {
            Sale s = new Sale();
            s.setId(dto.getSale());
            tc.setSale(s);
        }

        return tc;
    }
}
