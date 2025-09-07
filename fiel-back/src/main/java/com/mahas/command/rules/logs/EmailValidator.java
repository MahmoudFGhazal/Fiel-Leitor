package com.mahas.command.rules.logs;

import java.util.regex.Pattern;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.User;
import com.mahas.facade.Facade;

public class EmailValidator {
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

    public String emailExists(String email) {
        Facade facade = new Facade();

        User user = new User();
        user.setEmail(email);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);

        FacadeResponse response = facade.query(request);

        DomainEntity entities = response.getData().getEntity();

        if(entities != null) {
            return "E-mail já cadastrado";
        }

        return null;
    }
}
