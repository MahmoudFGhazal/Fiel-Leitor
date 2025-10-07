package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyDeleteAddress implements IPreCommand {
    @Autowired
    private AddressValidator addressValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof AddressDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado AddressDTORequest");
        }

        AddressDTORequest addressRequest = (AddressDTORequest) entity;

        communValidator.validateNotBlack(addressRequest.getId() == null ? null : addressRequest.getId().toString(), "Id");

        // Verificar se o endereço existe
        if (!addressValidator.addressExists(addressRequest.getId())) {
            throw new ValidationException("Endereço não encontrado");
        }

        Address address = new Address();

        address.setId(addressRequest.getId());

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(address);
        return sqlRequest;
    }
}
