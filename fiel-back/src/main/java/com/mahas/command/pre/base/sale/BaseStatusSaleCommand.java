package com.mahas.command.pre.base.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.StatusSaleValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.StatusSale;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.StatusSaleDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseStatusSaleCommand implements IPreCommand {
    @Autowired
    @Lazy
    StatusSaleValidator statusSaleValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof StatusSaleDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado StatusSaleDTORequest");
        }

        StatusSaleDTORequest statusSaleRequest = (StatusSaleDTORequest) entity;
        StatusSale statusSale = statusSaleValidator.toEntity(statusSaleRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(statusSale);

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
