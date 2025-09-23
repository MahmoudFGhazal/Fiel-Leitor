package com.mahas.command.pre.base.user;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.GenderValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.Gender;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseGenderCommand implements IPreCommand {
    @Autowired
    @Lazy
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

        if(request.getLimit() != null && request.getLimit() > 0) {
            sqlRequest.setLimit(request.getLimit());
            if(request.getPage() != null && request.getPage() > 0) {
                sqlRequest.setPage(request.getPage());
            }else {
                sqlRequest.setPage(1);
            }
        }else {
            sqlRequest.setLimit(0);
            sqlRequest.setPage(1);
        }
        
        return sqlRequest;
    }
}
