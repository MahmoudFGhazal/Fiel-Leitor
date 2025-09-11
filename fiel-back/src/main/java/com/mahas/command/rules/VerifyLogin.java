package com.mahas.command.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.user.User;

@Component
public class VerifyLogin implements ICommand {
    @Override
    public String execute(FacadeRequest request) {
        User user = (User) request.getEntity();

        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            return "Email não especificado";
        }

        if(user.getPassword() == null|| user.getPassword().isEmpty()) {
            return "Senha não especificado";
        }

        return null;
    }
}
