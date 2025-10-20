package com.mahas.command.pre.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.base.sale.BaseTraderCouponCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.sale.TraderCouponDTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

@Component
public class TraderCouponValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseTraderCouponCommand baseTraderCouponCommand;
   
    public TraderCoupon toEntity(TraderCouponDTORequest dto) {
        if (dto == null) return null;

        TraderCoupon tc = new TraderCoupon();
        tc.setId(dto.getId() != null ? dto.getId() : null);
        tc.setCode(dto.getCode());
        tc.setValue(dto.getValue());
        tc.setUsed(dto.getUsed());

        if (dto.getAppliedSale() != null) {
            Sale s = new Sale();
            s.setId(dto.getAppliedSale());
            tc.setAppliedSale(s);
        }

        if (dto.getOriginSale() != null) {
            Sale s = new Sale();
            s.setId(dto.getOriginSale());
            tc.setOriginSale(s);
        }

        return tc;
    }

    public TraderCouponDTOResponse isUsed(Integer id) {
        FacadeRequest req = new FacadeRequest();

        TraderCouponDTORequest traderCouponReq = new TraderCouponDTORequest();
        traderCouponReq.setId(id);

        req.setEntity(traderCouponReq);
        req.setPreCommand(baseTraderCouponCommand);
        req.setLimit(1);

        FacadeResponse res = facade.query(req);

        if(res.getData().getEntity() == null) {
            throw new ValidationException("Cupom de troca não encontrado");
        }

        DTOResponse entity = res.getData().getEntity();
        TraderCouponDTOResponse traderCouponRes = (TraderCouponDTOResponse) entity;

        if(traderCouponRes.getUsed() == true) {
            throw new ValidationException("Cupom de troca já utilizado");
        }

        return traderCouponRes;
    }
}
