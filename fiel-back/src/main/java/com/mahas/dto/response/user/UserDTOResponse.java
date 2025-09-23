package com.mahas.dto.response.user;

import java.time.LocalDate;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.user.User;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse implements DTOResponse {
    private Integer id;
    private String name;
    private String email;
    private Boolean active;
    private GenderDTOResponse gender;
    private LocalDate birthday;
    private String cpf;
    private String phoneNumber;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof User u) {
            this.id = u.getId();
            this.name = u.getName();
            this.email = u.getEmail();
            this.active = u.getActive();

            if (u.getGender() != null) {
                this.gender = new GenderDTOResponse();
                this.gender.mapFromEntity(u.getGender());
            }

            this.birthday = u.getBirthday();
            this.cpf = u.getCpf();
            this.phoneNumber = u.getPhoneNumber();
        }
    }
}
