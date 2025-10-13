package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.sale.BasePromotionalCouponCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.dto.request.sale.PromotionalCouponDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.sale.PromotionalCouponDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionalCouponValidator {
    @Autowired
    Facade facade;

    @Autowired
    BasePromotionalCouponCommand basePromotionalCouponCommand;
   
    public PromotionalCoupon toEntity(PromotionalCouponDTORequest dto) {
        if (dto == null) return null;

        PromotionalCoupon pc = new PromotionalCoupon();
        pc.setId(dto.getId() != null ? dto.getId().intValue() : null);
        pc.setValue(dto.getValue());
        pc.setUsed(dto.getUsed());

        return pc;
    }

    public void isUsed(Integer id) {
        FacadeRequest req = new FacadeRequest();

        PromotionalCouponDTORequest promotionCouponReq = new PromotionalCouponDTORequest();
        promotionCouponReq.setId(id);

        req.setEntity(promotionCouponReq);
        req.setPreCommand(basePromotionalCouponCommand);
        req.setLimit(1);

        FacadeResponse res = facade.query(req);

        if(res.getData().getEntity() == null) {
            throw new ValidationException("Cupom promocional não encontrado");
        }

        DTOResponse entity = res.getData().getEntity();
        PromotionalCouponDTOResponse promotionalCouponRes = (PromotionalCouponDTOResponse) entity;

        if(promotionalCouponRes.getUsed() == true) {
            throw new ValidationException("Cupom promocional já utilizado");
        }
    }
}
