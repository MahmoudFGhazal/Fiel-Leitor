package com.mahas.command.pre.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        communValidator.validateNotBlanck(userRequest.getEmail(), "E-mail");
        communValidator.validateNotBlanck(userRequest.getCpf(), "CPF");
        communValidator.validateNotBlanck(userRequest.getBirthday(), "Data de Nascimento");
        communValidator.validateNotBlanck(userRequest.getPassword(), "Senha");
        communValidator.validateNotBlanck(userRequest.getPhoneNumber(), "Telefone");
        communValidator.validateNotBlanck(userRequest.getGender(), "Gênero");

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
