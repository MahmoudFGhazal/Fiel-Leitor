package com.mahas.command.pre.base.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.TraderCouponValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseTraderCouponCommand implements IPreCommand {
    @Autowired
    @Lazy
    TraderCouponValidator traderCouponValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof TraderCouponDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado TraderCouponDTORequest");
        }

        TraderCouponDTORequest traderCouponRequest = (TraderCouponDTORequest) entity;
        TraderCoupon traderCoupon = traderCouponValidator.toEntity(traderCouponRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(traderCoupon);

        if(request.getLimit() != null && request.getLimit() > 0) {
            sqlRequest.setLimit(request.getLimit());
            if(request.getPage() != null && request.getPage() > 0) {
                sqlRequest.setPage(request.getPage());
            }else {
                sqlRequest.setPage(1);
            }
        }else {
            sqlRequest.setLimit(0);
            sqlRequest.setPage(1);
        }
        
        return sqlRequest;
    }
}
