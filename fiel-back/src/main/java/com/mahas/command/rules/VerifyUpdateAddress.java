package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.AddressValidator;
import com.mahas.command.rules.logs.ResidenceTypeValidator;
import com.mahas.command.rules.logs.StreetTypeValidator;
import com.mahas.command.rules.logs.ZIPValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.exception.ValidationException;

@Component
public class VerifyUpdateAddress implements ICommand {
    @Autowired
    private AddressValidator addressValidator;

    @Autowired
    private ZIPValidator zipValidator;

    @Autowired
    private StreetTypeValidator streetTypeValidator;

    @Autowired
    private ResidenceTypeValidator residenceTypeValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        Address address = (Address) request.getEntity();
        String error;

        // Verificar se o endereço existe
        if (!addressValidator.addressExists(address.getId())) {
            throw new ValidationException("Endereço não encontrado");
        }

        // Validar formato do CEP
        error = zipValidator.isValidZIPFormat(address.getZip());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar tipo de rua
        if (!streetTypeValidator.streetTypeExists(address.getStreetType().getId())) {
            throw new ValidationException("Tipo de rua não encontrado");
        }

        // Validar tipo de residência
        if (!residenceTypeValidator.residenceTypeExists(address.getResidenceType().getId())) {
            throw new ValidationException("Tipo de residência não encontrada");
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(address);
        return sqlRequest;
    }
}
