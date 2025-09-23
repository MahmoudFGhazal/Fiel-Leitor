package com.mahas.command.pre.rules.logs;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

@Component
public class EmailValidator {
    @Autowired
    Facade facade;
    
    public String isValidEmailFormat(String email) {
        if (email == null || email.isBlank()) {
            return "Email não pode ser vazio";
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            return "Formato de email inválido";
        }

        return null;
    }

    public boolean emailExists(String email) {
        UserDTORequest user = new UserDTORequest();
        user.setEmail(email);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }
}
