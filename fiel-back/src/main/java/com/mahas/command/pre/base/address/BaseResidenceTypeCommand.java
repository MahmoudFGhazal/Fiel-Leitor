package com.mahas.command.pre.base.address;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.ResidenceTypeValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.ResidenceType;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.ResidenceTypeDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseResidenceTypeCommand implements IPreCommand {
    @Autowired
    @Lazy
    ResidenceTypeValidator residenceTypeValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof ResidenceTypeDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado ResidenceTypeDTORequest");
        }

        ResidenceTypeDTORequest residenceTypeRequest = (ResidenceTypeDTORequest) entity;
        ResidenceType residenceType = residenceTypeValidator.toEntity(residenceTypeRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(residenceType);

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
