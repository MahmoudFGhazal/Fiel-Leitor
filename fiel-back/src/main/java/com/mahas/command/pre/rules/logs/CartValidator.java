package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.domain.product.Book;
import com.mahas.domain.product.Cart;
import com.mahas.domain.user.User;
import com.mahas.dto.request.product.CartDTORequest;

@Component
public class CartValidator {
    public Cart toEntity(CartDTORequest dto) {
        if (dto == null) return null;

        Cart cart = new Cart();
        cart.setQuantity(dto.getQuatity());

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
}
