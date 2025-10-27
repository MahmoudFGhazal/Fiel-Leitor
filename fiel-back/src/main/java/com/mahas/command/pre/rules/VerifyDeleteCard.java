package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CardValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.Card;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyDeleteCard implements IPreCommand {
    @Autowired
    private CardValidator cardValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof CardDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado CardDTORequest");
        }

        CardDTORequest cardRequest = (CardDTORequest) entity;

        communValidator.validateNotBlanck(cardRequest.getId(), "Id");

        // Verificar se o usuário existe
        if (!cardValidator.cardExists(cardRequest.getId())) {
            throw new ValidationException("Cartão não encontrado");
        }

        Card card = new Card();

        card.setId(cardRequest.getId());
        card.setIsDelete(true);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(card);
        return sqlRequest;
    }
}
