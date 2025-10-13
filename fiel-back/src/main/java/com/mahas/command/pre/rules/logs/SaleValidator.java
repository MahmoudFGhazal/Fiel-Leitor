package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.sale.BaseSaleCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.domain.user.User;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseSaleCommand baseSaleCommand;
   
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

        if (dto.getPromotionalCoupons() != null) {
            for(Integer promotionalCoupon : dto.getPromotionalCoupons()) {
                PromotionalCoupon p = new PromotionalCoupon();
                p.setId(promotionalCoupon);
                sale.setPromotionalCoupon(p);
            }
        }

        return sale;
    }

    public boolean saleExists(Integer id) {
        SaleDTORequest sale = new SaleDTORequest();
        sale.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(sale);
        request.setLimit(1);
        request.setPreCommand(baseSaleCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

}
