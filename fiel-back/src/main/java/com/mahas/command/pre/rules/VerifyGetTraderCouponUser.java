package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.sale.TraderCouponDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyGetTraderCouponUser implements IPreCommand {
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof TraderCouponDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado TraderCouponDTORequest");
        }

        TraderCouponDTORequest traderCouponRequest = (TraderCouponDTORequest) entity;

        communValidator.validateNotBlanck(traderCouponRequest.getUser(), "Id do usuario não especificado");

        SQLRequest sqlRequest = new SQLRequest();

        userValidator.userExists(traderCouponRequest.getUser());

        TraderCoupon traderCoupon = new TraderCoupon();

        User user = new User();
        user.setId(traderCouponRequest.getUser());

        traderCoupon.setUser(user);

        sqlRequest.setEntity(traderCoupon);

        if(request.getLimit() != null && request.getLimit() > 0) {
            sqlRequest.setLimit(request.getLimit());
            if(request.getPage() != null && request.getPage() > 0) {
                sqlRequest.setPage(request.getPage());
            }else {
                sqlRequest.setPage(1);
            }
        }else {
            sqlRequest.setLimit(10);
            sqlRequest.setPage(1);
        }

        return sqlRequest;
    }
}
