package com.mahas.dto.request.product;

import java.math.BigDecimal;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTORequest implements DTORequest {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Boolean active;
    private Integer stock;
    private Integer category;
}
