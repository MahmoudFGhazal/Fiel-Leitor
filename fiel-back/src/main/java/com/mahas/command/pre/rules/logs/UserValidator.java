package com.mahas.command.pre.rules.logs;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.mahas.command.pre.base.user.BaseUserCommand;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Gender;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.exception.ValidationException;
import com.mahas.facade.Facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    @Autowired
    Facade facade;

    @Autowired
    BaseUserCommand baseUserCommand;

    public User toEntity(UserDTORequest dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());

        if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
            user.setPassword(dto.getNewPassword());
        } else {
            user.setPassword(dto.getPassword());
        }
        
        user.setEmail(dto.getEmail());
        user.setActive(dto.getActive());
        user.setBirthday(dto.getBirthday());
        user.setCpf(dto.getCpf());
        user.setPhoneNumber(dto.getPhoneNumber());

        // Relacionamentos (carregados só pelo id)
        if (dto.getGender() != null) {
            Gender gender = new Gender();
            gender.setId(dto.getGender());
            user.setGender(gender);
        }

        return user;
    }
    
    public boolean userExists(Integer id) {
        UserDTORequest user = new UserDTORequest();
        user.setId(id);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }

    public void isValidEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new ValidationException("Formato de email inválido");
        }
    }

    public boolean emailExists(String email) {
        UserDTORequest user = new UserDTORequest();
        user.setEmail(email);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }

    public void isValidPasswordFormat(String password) {
        if (password.length() < 8) {
            throw new ValidationException("Senha deve conter pelo menos 8 caracteres");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Senha deve conter pelo menos uma letra maiúscula");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new ValidationException("Senha deve conter pelo menos uma letra minúscula");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new ValidationException("Senha deve conter pelo menos um caractere especial");
        }
    }

    public boolean checkPasswordCorrect(Integer id, String password) {
        UserDTORequest user = new UserDTORequest();
        user.setId(id);
        user.setPassword(password);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null; 
    }

    public void isValidPhoneNumberFormat(String phoneNumber) {
        if (phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            throw new ValidationException("Número de telefone inválido");
        }
    }

    public void isValidCPFFormat(String cpf) {
        if (cpf.length() != 11) {
            throw new ValidationException("Número de telefone inválido");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new ValidationException("CPF inválido: todos os dígitos são iguais");
        }

        isValidCPF(cpf);
    }

    private void isValidCPF(String cpf) {
        try {
            int sum = 0;
            int weight = 10;

            for (int i = 0; i < 9; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                sum += num * weight--;
            }

            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            if (firstDigit != Integer.parseInt(cpf.substring(9, 10))) {
                throw new ValidationException("CPF Invalido");
            }

            sum = 0;
            weight = 11;
            for (int i = 0; i < 10; i++) {
                int num = Integer.parseInt(cpf.substring(i, i + 1));
                sum += num * weight--;
            }

            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            if (secondDigit != Integer.parseInt(cpf.substring(10, 11))) {
                throw new ValidationException("CPF inválido: segundo dígito verificador incorreto");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Erro ao validar CPF");
        }
    }

    public boolean cpfExists(String cpf) {
        UserDTORequest user = new UserDTORequest();
        user.setCpf(cpf);

        FacadeRequest request = new FacadeRequest();
        request.setEntity(user);
        request.setLimit(1);
        request.setPreCommand(baseUserCommand);

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();

        return entity != null;
    }

    public void verifyBirthdayDate(LocalDate birthday) {
        if (!birthday.isBefore(LocalDate.now())) {
            throw new ValidationException("Data de nascimento deve ser anterior à data atual");
        }
    }
}
