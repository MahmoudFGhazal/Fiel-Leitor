package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.BirthdayValidator;
import com.mahas.command.rules.logs.GenderValidator;
import com.mahas.command.rules.logs.PhoneNumberValidator;
import com.mahas.command.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.exception.ValidationException;

@Component
public class VerifyUpdateUser implements ICommand {
    @Autowired
    private BirthdayValidator birthdayValidator;

    @Autowired
    private GenderValidator genderValidator;

    @Autowired
    private PhoneNumberValidator phoneNumberValidator;

    @Autowired
    private UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        User user = (User) request.getEntity();
        String error;

        // Verificar se ID do usuário foi fornecido
        if (user.getId() == null) {
            throw new ValidationException("Id do usuário não especificado");
        }

        // Verificar se o usuário existe
        if (!userValidator.userExists(user.getId())) {
            throw new ValidationException("Usuário não encontrado");
        }

        // Validar número de telefone
        error = phoneNumberValidator.isValidPhoneNumberFormat(user.getPhoneNumber());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar data de nascimento (anterior a hoje)
        error = birthdayValidator.verifyBirthdayDate(user.getBirthday());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar gênero
        if (user.getGender() == null || user.getGender().getId() == null) {
            throw new ValidationException("Gênero não informado");
        }

        error = genderValidator.validateGenderExists(user.getGender().getId());
        if (error != null) {
            throw new ValidationException(error);
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(user); 
        return sqlRequest;
    }
}
