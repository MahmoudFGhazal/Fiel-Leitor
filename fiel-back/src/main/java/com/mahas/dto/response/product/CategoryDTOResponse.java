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
    private String name;
    private Boolean active;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Category c) {
            this.id = c.getId();
            this.name = c.getName();
            this.active = c.getActive();
        }
    }
}