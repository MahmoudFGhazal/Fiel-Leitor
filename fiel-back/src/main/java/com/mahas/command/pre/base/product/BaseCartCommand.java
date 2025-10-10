package com.mahas.command.pre.base.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CartValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Cart;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.CartDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseCartCommand implements IPreCommand {
    @Autowired
    @Lazy
    CartValidator cartValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof CartDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado CartDTORequest");
        }

        CartDTORequest cartRequest = (CartDTORequest) entity;
        Cart cart = cartValidator.toEntity(cartRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(cart);

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
