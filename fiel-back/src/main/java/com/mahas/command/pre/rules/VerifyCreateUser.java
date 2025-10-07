package com.mahas.command.pre.rules;

import com.mahas.command.pre.IPreCommand;
import com.mahas.command.pre.rules.logs.CommunValidator;
import com.mahas.command.pre.rules.logs.GenderValidator;
import com.mahas.command.pre.rules.logs.UserValidator;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.user.User;
import com.mahas.dto.request.DTORequest;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyCreateUser implements IPreCommand {
    @Autowired
    private GenderValidator genderValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CommunValidator communValidator;

    @Override
    public SQLRequest execute(FacadeRequest request) {
        DTORequest entity = request.getEntity();

        if (!(entity instanceof UserDTORequest)) {
            throw new ValidationException("Tipo de entidade inválido, esperado UserDTORequest");
        }

        UserDTORequest userRequest = (UserDTORequest) entity;

        communValidator.validateNotBlack(userRequest.getEmail(), "E-mail");
        communValidator.validateNotBlack(userRequest.getCpf(), "CPF");
        communValidator.validateNotBlack(userRequest.getBirthday() == null ? null : userRequest.getBirthday().toString(), "Data de Nascimento");
        communValidator.validateNotBlack(userRequest.getPassword(), "Senha");
        communValidator.validateNotBlack(userRequest.getPhoneNumber(), "Telefone");
        communValidator.validateNotBlack(userRequest.getGender() == null ? null : userRequest.getGender().toString(), "Gênero");

        SQLRequest sqlRequest = new SQLRequest();

        // Validar formato do email
        userValidator.isValidEmailFormat(userRequest.getEmail());

        // Verificar se email já existe
        if (userValidator.emailExists(userRequest.getEmail())) {
            throw new ValidationException("E-mail já cadastrado");
        }

        // Verificar formato da senha
        userValidator.isValidPasswordFormat(userRequest.getPassword());

        // Verificar se CPF já existe
        if (userValidator.cpfExists(userRequest.getCpf())) {
            throw new ValidationException("CPF já cadastrado");
        }

        // Validar data de nascimento (anterior a hoje)
        userValidator.verifyBirthdayDate(userRequest.getBirthday());

        // Validar gênero
        if (!genderValidator.genderExists(userRequest.getGender())) {
            throw new ValidationException("Genero não encontrado");
        }
    
        User user = userValidator.toEntity(userRequest);
    
        String cpf = communValidator.toNumeric(userRequest.getCpf());
        userValidator.isValidCPFFormat(userRequest.getCpf());
        user.setCpf(cpf);

        String phoneNumber = communValidator.toNumeric(userRequest.getPhoneNumber());
        userValidator.isValidPhoneNumberFormat(phoneNumber);
        user.setPhoneNumber(phoneNumber);

        user.setActive(true);

        sqlRequest.setEntity(user);

        return sqlRequest;
    }
}
