package com.mahas.command.rules;

import org.springframework.stereotype.Component;

import com.mahas.command.ICommand;
import com.mahas.command.rules.logs.BirthdayValidator;
import com.mahas.command.rules.logs.CPFValidator;
import com.mahas.command.rules.logs.EmailValidator;
import com.mahas.command.rules.logs.GenderValidator;
import com.mahas.command.rules.logs.PasswordValidator;
import com.mahas.command.rules.logs.PhoneNumberValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.user.User;

@Component
public class VerifyCreateUser implements ICommand {
    @Override
    public String execute(FacadeRequest request) {
        User user = (User) request.getEntity();

        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator();
        BirthdayValidator birthdayValidator = new BirthdayValidator();
        GenderValidator genderValidator = new GenderValidator();
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
        CPFValidator cpfValidator = new CPFValidator();

        String error;

        // Validar formato do email
        error = emailValidator.isValidEmailFormat(user.getEmail());
        if (error != null) {
            return error;
        }

        // Verificar se email já existe
        error = emailValidator.emailExists(user.getEmail());
        if (error != null) {
            return error;
        }

        //Verificar formato da senha
        error = passwordValidator.isValidPasswordFormat(user.getPassword());
        if (error != null) {
            return error;
        }

        // Validar CPF
        error = cpfValidator.isValidCPFFormat(user.getCpf());
        if (error != null) {
            return error;
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
