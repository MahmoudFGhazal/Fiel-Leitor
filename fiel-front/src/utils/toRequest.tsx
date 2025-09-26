import { AddressRequest, UserRequest } from "@/api/dtos/requestDTOs";
import { AddressResponse, UserResponse } from "@/api/dtos/responseDTOs";


export function toRequestUser(user: UserResponse | null): UserRequest {
    if(!user) {
        return {
            id: null,
            email: null,
            password: null,
            name: null,
            active: null,
            gender: null,
            birthday: null,
            cpf: null,
            phoneNumber: null
        };
    }
    
    return {
        id: user.id,
        email: user.email,
        password: null,
        name: user.name,
        active: user.active,
        gender: user.gender?.id ?? null,
        birthday: user.birthday,
        cpf: user.cpf,
        phoneNumber: user.phoneNumber
    };
}

export function toRequestAddress(address: AddressResponse | null): AddressRequest {
    if(!address) {
        return {
            id: null,
            user: null,
            nickname: null,
            number: null,
            complement: null,
            street: null,
            neighborhood: null,
            zip: null,
            city: null,
            state: null,
            country: null,
            streetType: null,
            residenceType: null
        };
    }
    
    return {
        id: address.id,
        user: address.user?.id ?? null,
        nickname: address.nickname,
        number: address.number,
        complement: address.complement,
        street: address.street,
        neighborhood: address.neighborhood,
        zip: address.zip,
        city: address.city,
        state: address.state,
        country: address.country,
        streetType: address.streetType?.id ?? null,
        residenceType: address.residenceType?.id ?? null
    }
}