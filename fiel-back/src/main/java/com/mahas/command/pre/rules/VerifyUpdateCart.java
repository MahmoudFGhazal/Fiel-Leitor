package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BookValidator;
import com.mahas.command.pre.rules.logs.CartValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Cart;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.CartDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyUpdateCart implements IPreCommand {
    @Autowired
    private CartValidator cartValidator;

    @Autowired
    private BookValidator bookValidator;

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
        communValidator.validateNotBlanck(cartRequest.getQuantity(), "Quantidade");        

        if(!cartValidator.cartExists(cartRequest.getBook(), cartRequest.getUser())) {
            throw new ValidationException(cartRequest.getBook() + " não está no carrinho");
        }

        if(cartRequest.getQuantity() < 0) {
            throw new ValidationException("Quantidade");
        }

        bookValidator.checkBookStock(cartRequest.getBook(), cartRequest.getQuantity()); 

        SQLRequest sqlRequest = new SQLRequest();

        Cart cart = cartValidator.toEntity(cartRequest);

        sqlRequest.setEntity(cart);
        
        return sqlRequest;
    }
}
