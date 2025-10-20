package com.mahas.dto.request.sale;

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
public class TraderCouponDTORequest implements DTORequest {
    private Integer id;
    private Integer originSale;
    private Integer appliedSale;
    private String code;
    private BigDecimal value;
    private Boolean used;
}
