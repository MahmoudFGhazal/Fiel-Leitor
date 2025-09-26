export interface AddressRequest {
    id: number | null,
    user: number | null,
    nickname: string | null,
    number: string | null,
    complement: string | null,
    street: string | null,
    neighborhood: string | null,
    zip: string | null,
    city: string | null,
    state: string | null,
    country: string | null,
    streetType: number | null,
    residenceType: number | null
}

export interface CardRequest {
    id: number | null,
    user: number | null,
    principal: boolean | null,
    number: string | null,
    ccv: string | null,
    holder: string | null,
    valid: string | null,
    paymentType: number | null
}

export interface GenderRequest {
    id: number | null,
    gender: string | null
}

export interface PaymentTypeRequest {
    id: number | null,
    paymentType: string | null
}

export interface ResidenceTypeRequest {
    id: number | null,
    residenceType: string | null
}

export interface StreetTypeRequest {
    id: number | null,
    streetType: string | null
}

export interface UserRequest {
    id: number | null,
    email: string | null,
    password: string | null,
    name: string | null,
    active: boolean | null,
    gender: number | null,
    birthday: Date | null,
    cpf: string | null,
    phoneNumber: string | null
}

export interface SignRequest {
    user: UserRequest,
    address: AddressRequest
}