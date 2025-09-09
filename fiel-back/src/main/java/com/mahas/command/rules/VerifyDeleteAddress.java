package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.AddressValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.address.Address;

@Component
public class VerifyDeleteAddress implements ICommand {
    @Autowired
    AddressValidator addressValidator;

    @Override
    public String execute(FacadeRequest request) {
        Address address = (Address) request.getEntity();
        System.out.println(address.getId());
        if(address.getId() == null) {
            return "Id do endereço não especificado";
        }

        // Verificar se usuario existe
        if (!addressValidator.addressExists(address.getId())) {
            return "Endereço não encontrado";
        }

        return null;
    }
}
