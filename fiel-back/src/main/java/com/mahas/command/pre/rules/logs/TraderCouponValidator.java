package com.mahas.command.pre.rules.logs;

import java.security.SecureRandom;

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

    private static final SecureRandom RNG = new SecureRandom();
    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public String randomCode(int length) {
        if (length <= 0) throw new IllegalArgumentException("length must be > 0");
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RNG.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public String randomUniqueCode() {
        int length = 5;

        String code;
        int attempts = 0;
        do {
            if (++attempts > 1000) {
                length++;
                attempts = 0;
            }
            code = randomCode(length);
        } while (existsByCode(code));

        return code;
    }

    public boolean existsByCode(String code) {
        FacadeRequest req = new FacadeRequest();

        TraderCouponDTORequest traderCoupon = new TraderCouponDTORequest();
        traderCoupon.setCode(code); 

        req.setEntity(traderCoupon);
        req.setPreCommand(baseTraderCouponCommand);
        req.setLimit(1);

        FacadeResponse res = facade.query(req);

        boolean hasSingle = res.getData() != null && res.getData().getEntity() != null;
      
        return hasSingle;
    }
}
