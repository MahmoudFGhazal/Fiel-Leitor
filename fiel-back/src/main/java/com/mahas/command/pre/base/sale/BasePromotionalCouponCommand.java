package com.mahas.command.pre.base.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.PromotionalCouponValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.PromotionalCouponDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BasePromotionalCouponCommand implements IPreCommand {
    @Autowired
    @Lazy
    PromotionalCouponValidator promotionalCouponValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof PromotionalCouponDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado PromotionalCouponDTORequest");
        }

        PromotionalCouponDTORequest promotionalCouponRequest = (PromotionalCouponDTORequest) entity;
        PromotionalCoupon promotionalCoupon = promotionalCouponValidator.toEntity(promotionalCouponRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(promotionalCoupon);

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
