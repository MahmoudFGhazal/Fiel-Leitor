package com.mahas.dto.response.sale;

import java.math.BigDecimal;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.sale.SaleBook;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.product.BookDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleBookDTOResponse implements DTOResponse {
    private SaleDTOResponse sale;
    private BookDTOResponse book;
    private Integer quantity;
    private BigDecimal price;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof SaleBook sb) {
            if (sb.getSale() != null) {
                this.sale = new SaleDTOResponse();
                this.sale.mapFromEntity(sb.getSale());
            }

            if (sb.getBook() != null) {
                this.book = new BookDTOResponse();
                this.book.mapFromEntity(sb.getBook());
            }

            this.quantity = sb.getQuantity();
            this.price = sb.getPrice();
        }
    }

    public void mapFromEntity(DomainEntity entity, boolean sale) {
        if (entity instanceof SaleBook sb) {
            if(sale) {
                if (sb.getSale() != null) {
                    this.sale = new SaleDTOResponse();
                    this.sale.mapFromEntity(sb.getSale());
                }
            }

            if (sb.getBook() != null) {
                this.book = new BookDTOResponse();
                this.book.mapFromEntity(sb.getBook());
            }

            this.quantity = sb.getQuantity();
            this.price = sb.getPrice();
        }
    }
}