package com.mahas.command.post.adapters;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.product.Cart;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.dto.response.product.CartDTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;
import com.mahas.exception.ValidationException;

import org.springframework.stereotype.Component;

@Component
public class AddCartAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Cart)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Cart");
        }

        Cart cart = (Cart) entity;

        CartDTOResponse cartResponse = new CartDTOResponse();
        
        BookDTOResponse book = new BookDTOResponse();
        book.setId(cart.getBook().getId());

        UserDTOResponse user = new UserDTOResponse();
        user.setId(cart.getUser().getId());
        
        cartResponse.setBook(book);
        cartResponse.setUser(user);

        DataResponse data = new DataResponse();
        data.setEntity(cartResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
