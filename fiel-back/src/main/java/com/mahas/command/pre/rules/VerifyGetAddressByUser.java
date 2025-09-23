package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.AddressValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.address.Address;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyGetAddressByUser implements IPreCommand {
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

        Address address = addressValidator.toEntity(addressRequest);
       
        User user = address.getUser();

        // Verificar se ID do usuário foi fornecido
        if (user.getId() == null) {
            throw new ValidationException("Id do usuário não especificado");
        }

        // Verificar se o usuário existe
        if (!userValidator.userExists(user.getId())) {
            throw new ValidationException("Usuário não encontrado");
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(address);
        
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
