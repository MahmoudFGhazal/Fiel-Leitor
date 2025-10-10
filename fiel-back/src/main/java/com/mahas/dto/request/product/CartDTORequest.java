package com.mahas.dto.request.product;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTORequest implements DTORequest {
    private Integer user;
    private Integer book;
    private Integer quantity;
}
