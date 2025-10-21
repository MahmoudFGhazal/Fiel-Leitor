package com.mahas.command.pre.rules;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CardValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.SaleCardValidator;
import com.mahas.command.pre.rules.logs.SaleValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.SaleCard;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleCardDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCreateSaleCard implements IPreCommand {
    @Autowired
    private SaleCardValidator saleCardValidator;

    @Autowired
    private CardValidator cardValidator;

    @Autowired
    private SaleValidator saleValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof SaleCardDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado SaleCardDTORequest");
        }

        SaleCardDTORequest saleCardRequest = (SaleCardDTORequest) entity;

        communValidator.validateNotBlanck(saleCardRequest.getSale(), "Venda");
        communValidator.validateNotBlanck(saleCardRequest.getCard(), "Cartão");
        communValidator.validateNotBlanck(saleCardRequest.getPercent(), "Porcentagem");

        //Validar Cartão
        cardValidator.cardExists(saleCardRequest.getCard());
        
        //Validar Vendas
        saleValidator.saleExists(saleCardRequest.getSale());

        //Validar Percent
        if (saleCardRequest.getPercent() == null || saleCardRequest.getPercent().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Porcentagem inválida");
        }

        SaleCard saleCard = saleCardValidator.toEntity(saleCardRequest);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(saleCard);

        return sqlRequest;
    }
}
