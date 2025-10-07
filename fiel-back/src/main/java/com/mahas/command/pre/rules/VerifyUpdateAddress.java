package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.ResidenceTypeValidator;
import com.mahas.command.pre.rules.logs.StreetTypeValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyUpdateAddress implements IPreCommand {
    @Autowired
    private CommunValidator communValidator;

    @Autowired
    private AddressValidator addressValidator;

    @Autowired
    private StreetTypeValidator streetTypeValidator;

    @Autowired
    private ResidenceTypeValidator residenceTypeValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof AddressDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado AddressDTORequest");
        }

        AddressDTORequest addressRequest = (AddressDTORequest) entity;

        Address address = addressValidator.toEntity(addressRequest);
        
        // Verificar se o endereço existe
        if (!addressValidator.addressExists(address.getId())) {
            throw new ValidationException("Endereço não encontrado");
        }

        // Validar tipo de rua
        if (!streetTypeValidator.streetTypeExists(address.getStreetType().getId())) {
            throw new ValidationException("Tipo de rua não encontrado");
        }

        // Validar tipo de residência
        if (!residenceTypeValidator.residenceTypeExists(address.getResidenceType().getId())) {
            throw new ValidationException("Tipo de residência não encontrada");
        }

        String zip = communValidator.toNumeric(address.getZip());
        addressValidator.isValidZIPFormat(zip);
        address.setZip(zip);

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(address);
        
        return sqlRequest;
    }
}
