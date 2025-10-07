package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CardValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.Card;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyCreateCard implements IPreCommand {
    @Autowired
    CardValidator cardValidator;

    @Autowired
    UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof CardDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado CardDTORequest");
        }

        CardDTORequest cardRequest = (CardDTORequest) entity;

        Card card = cardValidator.toEntity(cardRequest);

        // Validar Usuario existe
        if (!userValidator.userExists(cardRequest.getUser())) {
            throw new ValidationException("Usuário não encontrado");
        }

        // Validar se já existe esse cartão
        if (!userValidator.userExists(cardRequest.getUser())) {
            throw new ValidationException("Usuário não encontrado");
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(card);
        return sqlRequest;
    }
}
