package com.mahas.dto.response.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.sale.Sale;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTOResponse implements DTOResponse {
    private Integer id;
    private UserDTOResponse user;
    private BigDecimal freight;
    private LocalDate deliveryDate;
    private List<SaleBookDTOResponse> saleBooks;
    private StatusSaleDTOResponse statusSale;
    private PromotionalCouponDTOResponse promotionalCoupon;
    private List<TraderCouponDTOResponse> traderCoupons;
    private AddressDTOResponse address;
    private LocalDateTime createdAt;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Sale s) {
            this.id = s.getId();
            this.freight = s.getFreight();
            this.deliveryDate = s.getDeliveryDate();
            
            if (s.getUser() != null) {
                this.user = new UserDTOResponse();
                this.user.mapFromEntity(s.getUser());
            }

            if (s.getStatusSale() != null) {
                this.statusSale = new StatusSaleDTOResponse();
                this.statusSale.mapFromEntity(s.getStatusSale());
            }

            if (s.getPromotionalCoupon() != null) {
                this.promotionalCoupon = new PromotionalCouponDTOResponse();
                this.promotionalCoupon.mapFromEntity(s.getPromotionalCoupon());
            }

            if (s.getSaleBooks() != null && !s.getSaleBooks().isEmpty()) {
                this.saleBooks = s.getSaleBooks()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(sb -> {
                        SaleBookDTOResponse dto = new SaleBookDTOResponse();
                        dto.mapFromEntity(sb, false);
                        return dto;
                    })
                    .collect(Collectors.toList());
            } else {
                this.traderCoupons = List.of();
            }

            if (s.getTraderCoupons() != null && !s.getTraderCoupons().isEmpty()) {
                this.traderCoupons = s.getTraderCoupons()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(tc -> {
                        TraderCouponDTOResponse dto = new TraderCouponDTOResponse();
                        dto.mapFromEntity(tc);
                        return dto;
                    })
                    .collect(Collectors.toList());
            } else {
                this.traderCoupons = List.of();
            }

            if (s.getAddress() != null) {
                this.address = new AddressDTOResponse();
                this.address.mapFromEntity(s.getAddress());
            }

            if(s.getCreatedAt() != null) {
                this.createdAt = s.getCreatedAt();
            }
        }
    }
}