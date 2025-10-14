package com.mahas.dto.request.product.cart;

import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.CartDTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartRequest implements DTORequest {
    private Integer userId;
    private CartDTORequest[] addIds;
}
