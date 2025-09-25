export interface AddressResponse {
    id: number | null,
    user: UserResponse | null,
    nickname: string | null,
    number: string | null,
    complement: string | null,
    street: string | null,
    neighborhood: string | null,
    zip: string | null,
    city: string | null,
    state: string | null,
    country: string | null,
    streetType: StreetTypeResponse | null,
    residenceType: ResidenceTypeResponse | null
}

export interface CardResponse {
    id: number | null,
    user: UserResponse | null,
    principal: boolean | null,
    number: string | null,
    ccv: string | null,
    holder: string | null,
    valid: string | null,
    paymentType: PaymentTypeResponse | null
}

export interface GenderResponse {
    id: number | null,
    gender: string | null
}

export interface PaymentTypeResponse {
    id: number | null,
    paymentType: string | null
}

export interface ResidenceTypeResponse {
    id: number | null,
    residenceType: string | null
}

export interface StreetTypeResponse {
    id: number | null,
    streetType: string | null
}

export interface UserResponse {
    id: number | null,
    email: string | null,
    name: string | null,
    active: boolean | null,
    gender: GenderResponse | null,
    birthday: Date | null,
    cpf: string | null,
    phoneNumber: string | null
}