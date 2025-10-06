package com.mahas.dto.response.product;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.Cart;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTOResponse implements DTOResponse {
    private UserDTOResponse user;
    private BookDTOResponse book;
    private Integer quantity;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Cart c) {
            if (c.getUser() != null) {
                this.user = new UserDTOResponse();
                this.user.mapFromEntity(c.getUser());
            }

            if (c.getBook() != null) {
                this.book = new BookDTOResponse();
                this.book.mapFromEntity(c.getBook());
            }

            this.quantity = c.getQuantity();
        }
    }
}