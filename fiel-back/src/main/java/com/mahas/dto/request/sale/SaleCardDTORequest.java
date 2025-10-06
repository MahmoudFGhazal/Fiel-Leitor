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
public class SaleCardDTORequest implements DTORequest {
    private Integer sale;
    private Integer card;
    private Double percent;
}
