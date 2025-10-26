package com.mahas.command.pre.rules.logs;

import org.springframework.stereotype.Component;

import com.mahas.domain.product.Category;
import com.mahas.dto.request.product.CategoryDTORequest;

@Component
public class CategoryValidator {
    public Category toEntity(CategoryDTORequest dto) {
    if (dto == null) return null;

        Category c = new Category();
        c.setId(dto.getId() != null ? dto.getId().intValue() : null);
        c.setCategory(dto.getCategory());
        c.setActive(dto.getActive());

        return c;
    }
}
