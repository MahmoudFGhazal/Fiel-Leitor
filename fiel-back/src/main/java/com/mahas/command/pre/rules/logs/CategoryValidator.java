package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.product.BaseCategoryCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.product.Category;
import com.mahas.dto.request.product.CategoryDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseCategoryCommand baseCategoryCommand;
   
    public Category toEntity(CategoryDTORequest dto) {
        if (dto == null) return null;

        Category c = new Category();
        c.setId(dto.getId() != null ? dto.getId().intValue() : null);
        c.setCategory(dto.getCategory());
        c.setActive(dto.getActive());

        return c;
    }

    public boolean categoryExists(Integer id) {
        CategoryDTORequest category = new CategoryDTORequest();
        category.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(category);
        request.setLimit(1);
        request.setPreCommand(baseCategoryCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }
}
