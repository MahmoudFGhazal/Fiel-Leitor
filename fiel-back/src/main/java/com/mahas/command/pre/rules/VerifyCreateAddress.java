package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.ResidenceTypeValidator;
import com.mahas.command.pre.rules.logs.StreetTypeValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCreateAddress implements IPreCommand {
    @Autowired
    private StreetTypeValidator streetTypeValidator;

    @Autowired
    private ResidenceTypeValidator residenceTypeValidator;

    @Autowired
    private UserValidator userValidator;

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
        
        communValidator.validateNotBlanck(addressRequest.getUser(), "Usuario");
        communValidator.validateNotBlanck(addressRequest.getNickname(), "Apelido");
        communValidator.validateNotBlanck(addressRequest.getNumber(), "Número");
        communValidator.validateNotBlanck(addressRequest.getStreet(), "Rua");
        communValidator.validateNotBlanck(addressRequest.getNeighborhood(), "Bairro");
        communValidator.validateNotBlanck(addressRequest.getZip(), "CEP");
        communValidator.validateNotBlanck(addressRequest.getCity(), "Cidade");
        communValidator.validateNotBlanck(addressRequest.getState(), "Estado");
        communValidator.validateNotBlanck(addressRequest.getCountry(), "País");
        communValidator.validateNotBlanck(addressRequest.getNeighborhood(), "Bairro");
        communValidator.validateNotBlanck(addressRequest.getStreetType(), "Tipo de rua");
        communValidator.validateNotBlanck(addressRequest.getResidenceType(), "Tipo de residencia");

        // Validar Tipo de Rua existe
        if (!streetTypeValidator.streetTypeExists(addressRequest.getStreetType())) {
            throw new ValidationException("Tipo de rua não encontrada");
        }

        // Validar Tipo de Residencia existe
        if (!residenceTypeValidator.residenceTypeExists(addressRequest.getResidenceType())) {
            throw new ValidationException("Tipo de residência não encontrada");
        }

        // Validar Usuario existe
        if (!userValidator.userExists(addressRequest.getUser())) {
            throw new ValidationException("Usuário não encontrado");
        }

        SQLRequest sqlRequest = new SQLRequest();

        String zip = communValidator.toNumeric(addressRequest.getZip());
        addressValidator.isValidZIPFormat(zip);
        addressRequest.setZip(zip);

        Address address = addressValidator.toEntity(addressRequest);

        if (!addressValidator.userHasAddress(addressRequest.getUser())) {
            address.setPrincipal(true);
        }else {
            address.setPrincipal(false);
        }

        sqlRequest.setEntity(address);
        
        return sqlRequest;
    }
}
