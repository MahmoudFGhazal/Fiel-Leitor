package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.BirthdayValidator;
import com.mahas.command.pre.rules.logs.CPFValidator;
import com.mahas.command.pre.rules.logs.EmailValidator;
import com.mahas.command.pre.rules.logs.GenderValidator;
import com.mahas.command.pre.rules.logs.PasswordValidator;
import com.mahas.command.pre.rules.logs.PhoneNumberValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

@Component
public class VerifyCreateUser implements IPreCommand {
    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private BirthdayValidator birthdayValidator;

    @Autowired
    private GenderValidator genderValidator;

    @Autowired
    private PhoneNumberValidator phoneNumberValidator;

    @Autowired
    private CPFValidator cpfValidator;

    @Autowired
    private UserValidator userValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        UserDTORequest userRequest = (UserDTORequest) request.getEntity();
        SQLRequest sqlRequest = new SQLRequest();

        String error;

        // Validar formato do email
        error = emailValidator.isValidEmailFormat(userRequest.getEmail());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Verificar se email já existe
        if (emailValidator.emailExists(userRequest.getEmail())) {
            throw new ValidationException("E-mail já cadastrado");
        }

        // Verificar formato da senha
        error = passwordValidator.isValidPasswordFormat(userRequest.getPassword());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar CPF
        error = cpfValidator.isValidCPFFormat(userRequest.getCpf());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Verificar se CPF já existe
        if (cpfValidator.cpfExists(userRequest.getCpf())) {
            throw new ValidationException("CPF já cadastrado");
        }

        // Validar número de telefone
        error = phoneNumberValidator.isValidPhoneNumberFormat(userRequest.getPhoneNumber());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar data de nascimento (anterior a hoje)
        error = birthdayValidator.verifyBirthdayDate(userRequest.getBirthday());
        if (error != null) {
            throw new ValidationException(error);
        }

        // Validar gênero
        if (userRequest.getGender() == null || userRequest.getGender() == null) {
            throw new ValidationException("Gênero não informado");
        }

        error = genderValidator.validateGenderExists(userRequest.getGender());
        if (error != null) {
            throw new ValidationException(error);
        }
    
        User user = userValidator.toEntity(userRequest);

        sqlRequest.setEntity(user);

        return sqlRequest;
    }
}
