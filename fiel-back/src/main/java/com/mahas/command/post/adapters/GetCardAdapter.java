package com.mahas.command.post.adapters;

import com.mahas.command.post.IPostCommand;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.user.Card;
import com.mahas.dto.response.user.CardDTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;
import com.mahas.exception.ValidationException;

import org.springframework.stereotype.Component;

@Component
public class GetCardAdapter implements IPostCommand {
    @Override
    public FacadeResponse execute(SQLResponse sqlResponse) {
        DomainEntity entity = sqlResponse.getEntity();

        if (!(entity instanceof Card)) {
            throw new ValidationException("Tipo de entidade inválido, esperado Card");
        }

        Card card = (Card) entity;

        CardDTOResponse cardResponse = new CardDTOResponse();
        cardResponse.setLast4(card.getLast4());

        UserDTOResponse userResponse = new UserDTOResponse();
        userResponse.setId(card.getUser().getId());
        cardResponse.setUser(userResponse);
        
        DataResponse data = new DataResponse();
        data.setEntity(cardResponse);

        FacadeResponse response = new FacadeResponse();
        response.setData(data);

        return response;
    }
}
