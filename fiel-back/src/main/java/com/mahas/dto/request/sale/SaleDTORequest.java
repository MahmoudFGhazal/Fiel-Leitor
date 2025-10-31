package com.mahas.dto.request.sale;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTORequest implements DTORequest {
    private Integer id;
    private Integer user;
    private BigDecimal freight;
    private LocalDate deliveryDate;
    private Integer status;
    private StatusSaleName statusName;
    private Integer address;
    private SaleCardDTORequest[] cards;
    private SaleBookDTORequest[] books;
    private Integer[] traderCoupons;
    private Integer promotionalCoupon;   
}
