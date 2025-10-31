package com.mahas.command.pre.rules;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyDefineTransport implements IPreCommand {
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
        communValidator.validateNotBlanck(saleRequest.getDeliveryDate(), "Data de entrega");
        communValidator.validateNotBlanck(saleRequest.getFreight(), "Frete");
        communValidator.validateNotBlanck(saleRequest.getStatusName(), "Status");

        saleValidator.saleExists(saleRequest.getId());

        BigDecimal freight = saleRequest.getFreight();
        if (freight.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Frete não pode ser menor que 0");
        }

        LocalDate deliveryDate = saleRequest.getDeliveryDate();
        if (deliveryDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Data de entrega não pode ser anterior a hoje");
        }
        
        SQLRequest sqlRequest = new SQLRequest();
    
        Sale sale = new Sale();

        sale.setId(saleRequest.getId());

        Integer saleStatusId = statusSaleValidator.getStatusSale(saleRequest.getStatusName());
        StatusSale status = new StatusSale();
        status.setId(saleStatusId);
        sale.setStatusSale(status);

        sale.setFreight(sale.getFreight());
        sale.setDeliveryDate(sale.getDeliveryDate());

        sqlRequest.setEntity(sale);

        return sqlRequest;
    }
}
