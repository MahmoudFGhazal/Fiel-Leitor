package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.ResidenceTypeValidator;
import com.mahas.command.rules.logs.StreetTypeValidator;
import com.mahas.command.rules.logs.UserValidator;
import com.mahas.command.rules.logs.ZIPValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.address.Address;

@Component
public class VerifyCreateAddress implements ICommand {
    @Autowired
    private ZIPValidator zipValidator;

    @Autowired
    private StreetTypeValidator streetTypeValidator;

    @Autowired
    private ResidenceTypeValidator residenceTypeValidator;

    @Autowired
    private UserValidator userValidator;

    @Override
    public String execute(FacadeRequest request) {
        Address address = (Address) request.getEntity();

        String error;

        // Validar formato do email
        error = zipValidator.isValidZIPFormat(address.getZip());
        if (error != null) {
            return error;
        }

        // Validar formato do email
        if (!streetTypeValidator.streetTypeExists(address.getStreetType().getId())) {
            return "Tipo de rua não encotrada";
        }

        // Validar formato do email
        if (!residenceTypeValidator.residenceTypeExists(address.getResidenceType().getId())) {
            return "Tipo de residencia não encontrada";
        }

        // Validar formato do email
        if (!userValidator.userExists(address.getUser().getId())) {
            return "Usuario não encontrado";
        }

        return null;
    }
}
