package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.TraderCouponValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.dto.response.sale.TraderCouponDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class VerifyUsedTraderCoupon implements IPreCommand {
    @Autowired
    private TraderCouponValidator traderCouponValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof TraderCouponDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado TraderCouponDTORequest");
        }

        TraderCouponDTORequest traderCouponRequest = (TraderCouponDTORequest) entity;

        communValidator.validateNotBlanck(traderCouponRequest.getId(), "Id não especificado");

        SQLRequest sqlRequest = new SQLRequest();

        TraderCouponDTOResponse tcRes = traderCouponValidator.isUsed(traderCouponRequest.getId());
        
        if(tcRes == null) {
            throw new ValidationException(": Cupom não encontrado");
        }

        TraderCoupon traderCoupon = new TraderCoupon();
        traderCoupon.setId(traderCouponRequest.getId());
        traderCoupon.setUsed(true);

        sqlRequest.setEntity(traderCoupon);

        return sqlRequest;
    }
}
