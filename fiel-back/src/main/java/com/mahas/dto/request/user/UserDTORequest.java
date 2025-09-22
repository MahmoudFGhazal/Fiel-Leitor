package com.mahas.dto.request.user;

import java.time.LocalDate;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORequest implements DTORequest {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private Boolean active;
    private Integer gender;
    private LocalDate birthday;
    private String cpf;
    private String phoneNumber;
}
