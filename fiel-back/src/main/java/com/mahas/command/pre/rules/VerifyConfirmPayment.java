package com.mahas.command.pre.rules;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
import com.mahas.dto.response.sale.PromotionalCouponDTOResponse;
import com.mahas.dto.response.sale.SaleBookDTOResponse;
import com.mahas.dto.response.sale.SaleDTOResponse;
import com.mahas.dto.response.sale.TraderCouponDTOResponse;
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

        communValidator.validateNotBlanck(saleRequest.getId().toString(), "Id não especificado");

        for(SaleCardDTORequest saleCard : saleRequest.getCards()) {
            communValidator.validateNotBlanck(saleCard.getCard().toString(), "Cartão");
            communValidator.validateNotBlanck(saleCard.getPercent().toString(), "Porcetagem do cartão");
        }

        SQLRequest sqlRequest = new SQLRequest();

        SaleDTOResponse res = saleValidator.saleExists(saleRequest.getId());

        if(res.getStatusSale().getStatus() == null ? StatusSaleName.PROCESSING.getValue() != null : !res.getStatusSale().getStatus().equals(StatusSaleName.PROCESSING.getValue())) {
            throw new ValidationException("Status não permite essa alteração");
        }

        BigDecimal discount = BigDecimal.ZERO;

        //Validar Cupom Troca
        if(saleRequest.getTraderCoupons() != null) {
            for(Integer couponId : saleRequest.getTraderCoupons()) {
                TraderCouponDTOResponse tc = traderCouponValidator.isUsed(couponId);

                BigDecimal price = tc.getValue() != null ? tc.getValue() : BigDecimal.ZERO;

                discount = discount.add(price.multiply(BigDecimal.valueOf(1)));
            }
        }

        //Validar Cupom de Promoção
        if(saleRequest.getPromotionalCoupon() != null) {
            PromotionalCouponDTOResponse pc = promotionalCouponValidator.isUsed(saleRequest.getPromotionalCoupon());

            BigDecimal price = pc.getValue() != null ? pc.getValue() : BigDecimal.ZERO;

            discount = discount.add(price.multiply(BigDecimal.valueOf(1)));
        }

        BigDecimal totalValue = BigDecimal.ZERO;

        List<SaleBookDTOResponse> items = res.getSaleBooks() != null ? res.getSaleBooks() : List.of();

        for (SaleBookDTOResponse sb : items) {
            if (sb == null) continue;

            BigDecimal price = sb.getPrice() != null ? sb.getPrice() : BigDecimal.ZERO;
            int qty = java.util.Objects.requireNonNullElse(sb.getQuantity(), 1);

            totalValue = totalValue.add(price.multiply(BigDecimal.valueOf(qty)));
        }

        //Validar Cartões
        saleCardValidator.checkPercent(saleRequest.getCards());

        Integer[] cardIds = Arrays.stream(saleRequest.getCards())
            .map(SaleCardDTORequest::getCard)  
            .toArray(Integer[]::new);

        cardValidator.isUser(saleRequest.getUser(), cardIds);
    
        BigDecimal payable = totalValue.subtract(discount);
        if (payable.signum() < 0) {
            payable = BigDecimal.ZERO;
        }

        final BigDecimal MIN_SPLIT = new BigDecimal("10.00");

        if (payable.compareTo(MIN_SPLIT) < 0) {
            if (saleRequest.getCards() == null || saleRequest.getCards().length != 1) {
                throw new ValidationException(
                    "Para compras com valor final menor que 10, é permitido apenas um cartão (100%)."
                );
            }

            SaleCardDTORequest only = saleRequest.getCards()[0];
            if (only.getPercent() == null || only.getPercent().compareTo(new BigDecimal("100")) != 0) {
                throw new ValidationException(
                    "Para compras com valor final menor que 10, o único cartão deve ter 100%."
                );
            }
        } else {
            saleCardValidator.checkPercent(saleRequest.getCards());
        }

        Sale sale = saleValidator.toEntity(saleRequest);
        sale.setUser(null);
        sale.setSaleBooks(null);

        Integer statusSaleId = statusSaleValidator.getStatusSale(StatusSaleName.APPROVED);
        StatusSale statusSale = new StatusSale();
        statusSale.setId(statusSaleId);
        sale.setStatusSale(statusSale);

        sqlRequest.setEntity(sale);

        return sqlRequest;
    }
}
