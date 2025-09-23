package com.mahas.command.pre.base.address;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.StreetTypeValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.StreetType;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.StreetTypeDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseStreetTypeCommand implements IPreCommand {
    @Autowired
    @Lazy
    StreetTypeValidator streetTypeValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof StreetTypeDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado StreetTypeDTORequest");
        }

        StreetTypeDTORequest streetTypeRequest = (StreetTypeDTORequest) entity;
        StreetType address = streetTypeValidator.toEntity(streetTypeRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(address);
        
        return sqlRequest;
    }
}
