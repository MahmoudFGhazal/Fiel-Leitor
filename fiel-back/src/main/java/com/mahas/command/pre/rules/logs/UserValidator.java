package com.mahas.command.pre.rules.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.user.Gender;
import com.mahas.domain.user.User;
import com.mahas.dto.request.user.UserDTORequest;
import com.mahas.dto.response.DTOResponse;
import com.mahas.facade.Facade;

@Component
public class UserValidator {
    @Autowired
    Facade facade;

    public User toEntity(UserDTORequest dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
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

        FacadeResponse response = facade.query(request);

        DTOResponse entity = response.getData().getEntity();
        
        return entity != null; 
    }
}
