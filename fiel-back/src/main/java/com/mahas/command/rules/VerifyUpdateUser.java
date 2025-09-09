package com.mahas.command.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.BirthdayValidator;
import com.mahas.command.rules.logs.GenderValidator;
import com.mahas.command.rules.logs.PhoneNumberValidator;
import com.mahas.command.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.user.User;

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
    public String execute(FacadeRequest request) {
        User user = (User) request.getEntity();

        String error;

        if(user.getId() == null) {
            return "Id do usuario não especificado";
        }

        // Verificar usuario existe
        if (!userValidator.userExists(user.getId())) {
            return "Usuario não encontrado";
        }

        // Validar número de telefone
        error = phoneNumberValidator.isValidPhoneNumberFormat(user.getPhoneNumber());
        if (error != null) {
            return error;
        }

        // Validar data de nascimento (anterior a hoje)
        error = birthdayValidator.verifyBirthdayDate(user.getBirthday());
        if (error != null) {
            return error;
        }

        // Validar se gênero existe (ID válido no banco)
        if (user.getGender() == null || user.getGender().getId() == null) {
            return "Gênero não informado";
        }

        error = genderValidator.validateGenderExists(user.getGender().getId());
        if (error != null) {
            return error;
        }

        return null;
    }
}
