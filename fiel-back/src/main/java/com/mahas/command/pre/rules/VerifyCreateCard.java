package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CardValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.Card;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCreateCard implements IPreCommand {
    @Autowired
    private CardValidator cardValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof CardDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado CardDTORequest");
        }

        CardDTORequest cardRequest = (CardDTORequest) entity;

        communValidator.validateNotBlanck(cardRequest.getBin(), "Número do cartão");
        communValidator.validateNotBlanck(cardRequest.getLast4(), "Número do cartão");
        communValidator.validateNotBlanck(cardRequest.getHolder(), "Titular");
        communValidator.validateNotBlanck(cardRequest.getExpMonth(), "Mês de expiração");
        communValidator.validateNotBlanck(cardRequest.getExpYear(), "Ano de expiração");
        communValidator.validateNotBlanck(cardRequest.getUser(), "Usuario");

        Card card = cardValidator.toEntity(cardRequest);

        // Validar Usuario existe
        if (!cardValidator.userHasCard(cardRequest.getUser())) {
            card.setPrincipal(true);
        }else {
            card.setPrincipal(false);
        }

        // Validar se já existe esse cartão
        if (!userValidator.userExists(cardRequest.getUser())) {
            throw new ValidationException("Usuário não encontrado");
        }

        String bin = communValidator.toNumeric(cardRequest.getBin());
        cardValidator.isValidBin(bin);
        card.setLast4(bin);

        String last4 = communValidator.toNumeric(cardRequest.getLast4());
        cardValidator.isValidLast4(last4);
        card.setLast4(last4);

        cardValidator.isValidExp(cardRequest.getExpMonth(), cardRequest.getExpYear());

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(card);
        return sqlRequest;
    }
}
