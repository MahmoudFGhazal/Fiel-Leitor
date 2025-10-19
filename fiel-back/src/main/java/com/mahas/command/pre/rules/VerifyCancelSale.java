package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.StatusSaleValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCancelSale implements IPreCommand {

    @Autowired
    private StatusSaleValidator statusSaleValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof SaleDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado SaleDTORequest");
        }

        SaleDTORequest saleRequest = (SaleDTORequest) entity;

        communValidator.validateNotBlack(saleRequest.getId().toString(), "Id não especificado");

        SQLRequest sqlRequest = new SQLRequest();

        Sale sale = new Sale();
        sale.setId(saleRequest.getId());

        Integer statusSaleId = statusSaleValidator.getStatusSale(StatusSaleName.DECLINED);
        StatusSale statusSale = new StatusSale();
        statusSale.setId(statusSaleId);
        sale.setStatusSale(statusSale);

        sqlRequest.setEntity(sale);

        return sqlRequest;
    }
}
