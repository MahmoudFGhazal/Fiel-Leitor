package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.command.pre.rules.logs.ResidenceTypeValidator;
import com.mahas.command.pre.rules.logs.StreetTypeValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.command.pre.rules.logs.ZIPValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCreateAddress implements IPreCommand {
    @Autowired
    private ZIPValidator zipValidator;

    @Autowired
    private StreetTypeValidator streetTypeValidator;

    @Autowired
    private ResidenceTypeValidator residenceTypeValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AddressValidator addressValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof AddressDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado AddressDTORequest");
        }

        AddressDTORequest addressRequest = (AddressDTORequest) entity;
        String error;

        //Validar ZIP
        error = zipValidator.isValidZIPFormat(addressRequest.getZip());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar formato do email
        if (!streetTypeValidator.streetTypeExists(addressRequest.getStreetType())) {
            throw new ValidationException("Tipo de rua não encontrada");
        }

        // Validar formato do email
        if (!residenceTypeValidator.residenceTypeExists(addressRequest.getResidenceType())) {
            throw new ValidationException("Tipo de residência não encontrada");
        }

        System.out.println(addressRequest.getUser());
        // Validar formato do email
        if (!userValidator.userExists(addressRequest.getUser())) {
            throw new ValidationException("Usuário não encontrado");
        }

        SQLRequest sqlRequest = new SQLRequest();

        String zip = zipValidator.convertZIP(addressRequest.getZip());
        addressRequest.setZip(zip);

        Address address = addressValidator.toEntity(addressRequest);

        sqlRequest.setEntity(address);
        
        return sqlRequest;
    }
}
