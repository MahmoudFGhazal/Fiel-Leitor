package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.SaleValidator;
import com.mahas.command.pre.rules.logs.TraderCouponValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCreateTraderCoupon implements IPreCommand {
    @Autowired
    private TraderCouponValidator traderCouponValidator;

    @Autowired
    private SaleValidator saleValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof TraderCouponDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado TraderCouponDTORequest");
        }

        TraderCouponDTORequest traderCouponRequest = (TraderCouponDTORequest) entity;

        communValidator.validateNotBlanck(traderCouponRequest.getOriginSale(), "Venda");
        communValidator.validateNotBlanck(traderCouponRequest.getValue(), "Valor");

        saleValidator.saleExists(traderCouponRequest.getOriginSale());

        String code = traderCouponValidator.randomUniqueCode();
 
        TraderCoupon traderCoupon = new TraderCoupon();
        traderCoupon.setCode(code);
        traderCoupon.setValue(traderCoupon.getValue());

        Sale sale = new Sale();
        sale.setId(traderCouponRequest.getOriginSale());

        traderCoupon.setOriginSale(sale);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(traderCoupon);
        return sqlRequest;
    }
}
