package com.mahas.command.pre.base.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.SaleCardValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.SaleCard;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleCardDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseSaleCardCommand implements IPreCommand {
    @Autowired
    @Lazy
    SaleCardValidator saleCardValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof SaleCardDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado SaleCardDTORequest");
        }

        SaleCardDTORequest saleRequest = (SaleCardDTORequest) entity;
        SaleCard saleCard = saleCardValidator.toEntity(saleRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(saleCard);

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
