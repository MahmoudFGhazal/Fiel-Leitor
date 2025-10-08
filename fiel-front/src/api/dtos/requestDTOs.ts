export interface AddressRequest {
    id: number | null,
    user: number | null,
    principal: boolean | null,
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

export interface BookRequest {
    id: number | null,
    name: string | null,
    price: number | null,
    active: boolean | null,
    stock: number | null,
    category: CategoryRequest | null
}

export interface CardRequest {
    id: number | null,
    user: number | null,
    principal: boolean | null,
    bin: string | null,
    last4: string | null,
    holder: string | null,
    expMonth: string | null,
    expYear: string | null
}

export interface CategoryRequest {
    id: number | null,
    name: string | null,
    active: boolean | null
}

export interface GenderRequest {
    id: number | null,
    gender: string | null
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
    newPassword: string | null,
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