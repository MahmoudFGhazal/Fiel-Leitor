package com.mahas.dto.response.product;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.Category;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTOResponse implements DTOResponse {
    private Integer id;
    private String category;
    private Boolean active;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Category c) {
            this.id = c.getId();
            this.category = c.getCategory();
            this.active = c.getActive();
        }
    }
}