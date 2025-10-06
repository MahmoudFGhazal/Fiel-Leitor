package com.mahas.command.pre.rules.logs;

import com.mahas.domain.address.Address;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.domain.user.User;
import com.mahas.dto.request.sale.SaleDTORequest;

import org.springframework.stereotype.Component;

@Component
public class SaleValidator {
    public Sale toEntity(SaleDTORequest dto) {
        if (dto == null) return null;

        Sale sale = new Sale();
        sale.setId(dto.getId() != null ? dto.getId().intValue() : null);
        sale.setFreight(dto.getFreight());
        sale.setDeliveryDate(dto.getDeliveryDate());

        if (dto.getUser() != null) {
            User u = new User();
            u.setId(dto.getUser());
            sale.setUser(u);
        }

        if (dto.getStatus() != null) {
            StatusSale s = new StatusSale();
            s.setId(dto.getStatus());
            sale.setStatusSale(s);
        }

        if (dto.getAddress() != null) {
            Address a = new Address();
            a.setId(dto.getAddress());
            sale.setAddress(a);
        }

        if (dto.getTraderCoupon() != null) {
            TraderCoupon t = new TraderCoupon();
            t.setId(dto.getTraderCoupon());
            sale.setTraderCoupon(t);
        }

        if (dto.getPromotionalCoupon() != null) {
            PromotionalCoupon p = new PromotionalCoupon();
            p.setId(dto.getPromotionalCoupon());
            sale.setPromotionalCoupon(p);
        }

        return sale;
    }
}
