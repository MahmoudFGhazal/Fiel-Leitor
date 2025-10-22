package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.PromotionalCouponValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.PromotionalCouponDTORequest;
import com.mahas.dto.response.sale.PromotionalCouponDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class VerifyUsedPromotionalCoupon implements IPreCommand {
    @Autowired
    private PromotionalCouponValidator promotionalCouponValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof PromotionalCouponDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado PromotionalCouponDTORequest");
        }

        PromotionalCouponDTORequest promotionalCouponRequest = (PromotionalCouponDTORequest) entity;

        communValidator.validateNotBlanck(promotionalCouponRequest.getId(), "Id não especificado");

        SQLRequest sqlRequest = new SQLRequest();

        PromotionalCouponDTOResponse pcRes = promotionalCouponValidator.isUsed(promotionalCouponRequest.getId());
        
        if(pcRes == null) {
            throw new ValidationException(": Cupom não encontrado");
        }

        PromotionalCoupon promotionalCoupon = new PromotionalCoupon();
        promotionalCoupon.setId(promotionalCouponRequest.getId());
        promotionalCoupon.setUsed(true);

        sqlRequest.setEntity(promotionalCoupon);

        return sqlRequest;
    }
}
