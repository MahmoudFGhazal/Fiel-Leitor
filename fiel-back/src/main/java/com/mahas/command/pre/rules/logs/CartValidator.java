package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.product.BaseCartCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.product.Book;
import com.mahas.domain.product.Cart;
import com.mahas.domain.user.User;
import com.mahas.dto.request.product.CartDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartValidator {
    @Autowired
    Facade facade;

    @Autowired
    private BaseCartCommand baseCartCommand;

    public Cart toEntity(CartDTORequest dto) {
        if (dto == null) return null;

        Cart cart = new Cart();
        cart.setQuantity(dto.getQuantity());

        if (dto.getUser() != null) {
            User u = new User();
            u.setId(dto.getUser());
            cart.setUser(u);
        }

        if (dto.getBook() != null) {
            Book b = new Book();
            b.setId(dto.getBook());
            cart.setBook(b);
        }

        return cart;
    }

    public boolean cartExists(Integer bookId, Integer userId) {
        CartDTORequest cart = new CartDTORequest();
        cart.setBook(bookId);
        cart.setUser(userId);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(cart);
        request.setLimit(1);
        request.setPreCommand(baseCartCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null; 
    }
}
