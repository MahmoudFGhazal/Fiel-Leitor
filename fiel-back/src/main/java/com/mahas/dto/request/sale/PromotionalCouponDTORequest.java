package com.mahas.dto.request.sale;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionalCouponDTORequest implements DTORequest {
    private Integer id;
    private Double value;
    private Boolean used;
}
