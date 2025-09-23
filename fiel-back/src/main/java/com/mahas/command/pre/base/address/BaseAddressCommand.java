package com.mahas.command.pre.base.address;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.dto.request.user.GenderDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseAddressCommand implements IPreCommand {
    @Autowired
    @Lazy
    AddressValidator addressValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof GenderDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado GenderDTORequest");
        }

        AddressDTORequest addressRequest = (AddressDTORequest) entity;
        Address address = addressValidator.toEntity(addressRequest);

        SQLRequest sqlRequest = new SQLRequest();

        sqlRequest.setEntity(address);
        
        return sqlRequest;
    }
}
