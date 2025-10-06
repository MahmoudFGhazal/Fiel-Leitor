package com.mahas.command.pre.base.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.SaleBookValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.SaleBook;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleBookDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseSaleBookCommand implements IPreCommand {
    @Autowired
    @Lazy
    SaleBookValidator saleBookValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof SaleBookDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado SaleBookDTORequest");
        }

        SaleBookDTORequest saleBookRequest = (SaleBookDTORequest) entity;
        SaleBook saleBook = saleBookValidator.toEntity(saleBookRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(saleBook);

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
