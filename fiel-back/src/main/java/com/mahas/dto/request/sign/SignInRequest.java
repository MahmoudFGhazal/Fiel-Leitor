package com.mahas.dto.request.sign;

import com.mahas.dto.request.address.AddressDTORequest;
import com.mahas.dto.request.user.UserDTORequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private UserDTORequest user;
    private AddressDTORequest address;
}