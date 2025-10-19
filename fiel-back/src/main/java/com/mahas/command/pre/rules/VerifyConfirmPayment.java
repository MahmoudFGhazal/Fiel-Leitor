package com.mahas.command.pre.rules;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CardValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.PromotionalCouponValidator;
import com.mahas.command.pre.rules.logs.SaleCardValidator;
import com.mahas.command.pre.rules.logs.SaleValidator;
import com.mahas.command.pre.rules.logs.StatusSaleValidator;
import com.mahas.command.pre.rules.logs.TraderCouponValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.StatusSaleName;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.SaleCardDTORequest;
import com.mahas.dto.request.sale.SaleDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyConfirmPayment implements IPreCommand {
    @Autowired
    private SaleValidator saleValidator;

    @Autowired
    private StatusSaleValidator statusSaleValidator;

    @Autowired
    private TraderCouponValidator traderCouponValidator;

    @Autowired
    private PromotionalCouponValidator promotionalCouponValidator;

    @Autowired
    private SaleCardValidator saleCardValidator;

    @Autowired
    private CardValidator cardValidator;

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

        for(SaleCardDTORequest saleCard : saleRequest.getCards()) {
            communValidator.validateNotBlack(saleCard.getCard().toString(), "Cartão");
            communValidator.validateNotBlack(saleCard.getPercent().toString(), "Porcetagem do cartão");
        }

        SQLRequest sqlRequest = new SQLRequest();

        //Validar Cupom Troca
        if(saleRequest.getTraderCoupons() != null) {
            for(Integer couponId : saleRequest.getTraderCoupons()) {
                traderCouponValidator.isUsed(couponId);
            }
        }

        //Validar Cupom de Promoção
        if(saleRequest.getPromotionalCoupon() != null) {
            promotionalCouponValidator.isUsed(saleRequest.getPromotionalCoupon());
        }

        //Validar Cartões
        saleCardValidator.checkPercent(saleRequest.getCards());

        Integer[] cardIds = Arrays.stream(saleRequest.getCards())
            .map(SaleCardDTORequest::getCard)  
            .toArray(Integer[]::new);

        cardValidator.isUser(saleRequest.getUser(), cardIds);
    
        Sale sale = saleValidator.toEntity(saleRequest);
        sale.setId(null);

        Integer statusSaleId = statusSaleValidator.getStatusSale(StatusSaleName.APPROVED);
        StatusSale statusSale = new StatusSale();
        statusSale.setId(statusSaleId);
        sale.setStatusSale(statusSale);

        sqlRequest.setEntity(sale);

        return sqlRequest;
    }
}
