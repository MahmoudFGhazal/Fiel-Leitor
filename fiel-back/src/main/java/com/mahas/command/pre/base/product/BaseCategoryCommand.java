package com.mahas.command.pre.base.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CategoryValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.product.Category;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.product.CategoryDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseCategoryCommand implements IPreCommand {
    @Autowired
    @Lazy
    CategoryValidator categoryValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof CategoryDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado CategoryDTORequest");
        }

        CategoryDTORequest categoryRequest = (CategoryDTORequest) entity;
        Category category = categoryValidator.toEntity(categoryRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(category);

        if(request.getLimit() != null && request.getLimit() > 0) {
            sqlRequest.setLimit(request.getLimit());
            if(request.getPage() != null && request.getPage() > 0) {
                sqlRequest.setPage(request.getPage());
            }else {
                sqlRequest.setPage(1);
            }
        }else {
            sqlRequest.setLimit(0);
            sqlRequest.setPage(1);
        }
        
        return sqlRequest;
    }
}
