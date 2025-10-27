package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CartValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Cart;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.CartDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyDeleteCart implements IPreCommand {
    @Autowired
    private CartValidator cartValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof CartDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado CartDTORequest");
        }

        CartDTORequest cartRequest = (CartDTORequest) entity;

        communValidator.validateNotBlanck(cartRequest.getUser(), "Usuario");
        communValidator.validateNotBlanck(cartRequest.getBook(), "Livro");

        if(!cartValidator.cartExists(cartRequest.getBook(), cartRequest.getUser())) {
            throw new ValidationException(cartRequest.getBook() + " não está no carrinho");
        }

        SQLRequest sqlRequest = new SQLRequest();

        Cart cart = cartValidator.toEntity(cartRequest);

        sqlRequest.setEntity(cart);
        
        return sqlRequest;
    }
}
