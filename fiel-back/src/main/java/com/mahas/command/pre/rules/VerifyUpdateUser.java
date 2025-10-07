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
public class VerifyUpdateUser implements IPreCommand {
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
       
        // Verificar se ID do usuário foi fornecido
        if (userRequest.getId() == null) {
            throw new ValidationException("Id do usuário não especificado");
        }

        // Verificar se o usuário existe
        if (!userValidator.userExists(userRequest.getId())) {
            throw new ValidationException("Usuário não encontrado");
        }

        // Validar data de nascimento (anterior a hoje)
        if(userRequest.getBirthday() != null) {
            userValidator.verifyBirthdayDate(userRequest.getBirthday());
        }

        // Validar gênero
        if (userRequest.getGender() != null) {
            if (!genderValidator.genderExists(userRequest.getGender())) {
                throw new ValidationException("Genero não encontrado");
            }
        }

        User user = userValidator.toEntity(userRequest);
        user.setPassword(null);
        user.setCpf(null);
        user.setEmail(null);

        if(userRequest.getPhoneNumber() != null) {
            String phoneNumber = communValidator.toNumeric(userRequest.getPhoneNumber());
            userValidator.isValidPhoneNumberFormat(phoneNumber);
            user.setPhoneNumber(phoneNumber);
        }

        SQLRequest sqlRequest = new SQLRequest();
        sqlRequest.setEntity(user); 
        return sqlRequest;
    }
}
