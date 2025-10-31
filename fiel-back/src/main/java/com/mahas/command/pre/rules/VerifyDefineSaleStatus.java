package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.SaleValidator;
import com.mahas.command.pre.rules.logs.StatusSaleValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.dto.response.sale.SaleDTOResponse;
import com.mahas.exception.ValidationException;

@Component
public class VerifyDefineSaleStatus implements IPreCommand {
    @Autowired
    private SaleValidator saleValidator;
    
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

        communValidator.validateNotBlanck(saleRequest.getId(), "Id");
        communValidator.validateNotBlanck(saleRequest.getStatusName(), "Status");

        SaleDTOResponse saleRes = saleValidator.saleExists(saleRequest.getId());

        if(saleRequest.getStatusName() == StatusSaleName.DELIVERED && (saleRes.getStatusSale().getStatus() == null ? StatusSaleName.IN_TRANSIT.getValue() != null : !saleRes.getStatusSale().getStatus().equals(StatusSaleName.IN_TRANSIT.getValue()))) {
            throw new ValidationException("Não é possivel deixar a função como entrega");
        } else if(saleRequest.getStatusName() == StatusSaleName.EXCHANGE_REQUESTED && (saleRes.getStatusSale().getStatus() == null ? StatusSaleName.DELIVERED.getValue() != null : !saleRes.getStatusSale().getStatus().equals(StatusSaleName.DELIVERED.getValue()))) {
            throw new ValidationException("Não é possivel pedir a troca desse produto");
        } else if(saleRequest.getStatusName() == StatusSaleName.EXCHANGE_AUTHORIZED && (saleRes.getStatusSale().getStatus() == null ? StatusSaleName.EXCHANGE_REQUESTED.getValue() != null : !saleRes.getStatusSale().getStatus().equals(StatusSaleName.EXCHANGE_REQUESTED.getValue()))) {
            throw new ValidationException("Não é possivel pedir a troca desse produto");
        } else if(saleRequest.getStatusName() == StatusSaleName.EXCHANGED && (saleRes.getStatusSale().getStatus() == null ? StatusSaleName.EXCHANGE_AUTHORIZED.getValue() != null : !saleRes.getStatusSale().getStatus().equals(StatusSaleName.EXCHANGE_AUTHORIZED.getValue()))) {
            throw new ValidationException("Não é possivel pedir a troca desse produto");
        } else if(saleRequest.getStatusName() != StatusSaleName.DELIVERED && saleRequest.getStatusName() != StatusSaleName.EXCHANGE_REQUESTED && saleRequest.getStatusName() != StatusSaleName.EXCHANGE_AUTHORIZED && saleRequest.getStatusName() != StatusSaleName.EXCHANGED && saleRequest.getStatusName() != StatusSaleName.DECLINED) {
            throw new ValidationException("Função não suporta esse mudança de status");
        }

        SQLRequest sqlRequest = new SQLRequest();
    
        Sale sale = new Sale();

        sale.setId(saleRequest.getId());

        Integer saleStatusId = statusSaleValidator.getStatusSale(saleRequest.getStatusName());
        StatusSale status = new StatusSale();
        status.setId(saleStatusId);
        sale.setStatusSale(status);

        sqlRequest.setEntity(sale);

        return sqlRequest;
    }
}
