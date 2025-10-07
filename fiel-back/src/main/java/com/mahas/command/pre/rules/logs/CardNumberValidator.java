package com.mahas.command.pre.rules.logs;

import com.mahas.command.pre.base.user.BaseCardCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.CardDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardNumberValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseCardCommand baseCardCommand;

    
    public void isValidNumberFormat(String bin, String last4) {
        if (bin.length() != 6) {
            throw new ValidationException("Bin inválido");
        }

        if (last4.length() != 6) {
            throw new ValidationException("Last4 inválido");
        }
    }

    public boolean cardNumberExists(String bin, String last4) {
        FacadeRequest request = new FacadeRequest();

        request.setPreCommand(baseCardCommand);
        CardDTORequest card = new CardDTORequest();
        card.setBin(bin);
        card.setLast4(last4);
        request.setEntity(card); 

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }
}
