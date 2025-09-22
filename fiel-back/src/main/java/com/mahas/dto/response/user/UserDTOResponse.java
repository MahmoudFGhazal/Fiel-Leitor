package com.mahas.dto.response.user;

import java.time.LocalDate;

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
}
