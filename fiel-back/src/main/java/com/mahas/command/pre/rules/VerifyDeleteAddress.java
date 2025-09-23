package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.exception.ValidationException;

@Component
public class VerifyDeleteAddress implements IPreCommand {
    @Autowired
    AddressValidator addressValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        Address address = (Address) request.getEntity();

        // Verificar se ID do endereço foi fornecido
        if (address.getId() == null) {
            throw new ValidationException("Id do endereço não especificado");
        }

        // Verificar se o endereço existe
        if (!addressValidator.addressExists(address.getId())) {
            throw new ValidationException("Endereço não encontrado");
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(address);
        return sqlRequest;
    }
}
