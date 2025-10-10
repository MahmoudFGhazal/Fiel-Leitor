package com.mahas.dto.response.product;

import java.math.BigDecimal;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.Book;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTOResponse implements DTOResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;
    private CategoryDTOResponse category;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Book b) {
            this.id = b.getId();
            this.name = b.getName();
            this.price = b.getPrice();
            this.stock = b.getStock();
            this.active = b.getActive();

            if (b.getCategory() != null) {
                this.category = new CategoryDTOResponse();
                this.category.mapFromEntity(b.getCategory());
            }
        }
    }
}
