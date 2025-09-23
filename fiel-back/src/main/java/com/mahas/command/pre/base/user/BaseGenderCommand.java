package com.mahas.command.pre.base.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.GenderValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.Gender;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class BaseGenderCommand implements IPreCommand {
    @Autowired
    GenderValidator genderValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof GenderDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado GenderDTORequest");
        }

        GenderDTORequest genderRequest = (GenderDTORequest) entity;
        Gender gender = genderValidator.toEntity(genderRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(gender);
        
        return sqlRequest;
    }
}
