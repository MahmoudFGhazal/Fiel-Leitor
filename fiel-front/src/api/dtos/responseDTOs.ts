export interface AddressResponse {
    id: number | null,
    user: UserResponse | null,
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
    streetType: StreetTypeResponse | null,
    residenceType: ResidenceTypeResponse | null
}

export interface BookResponse {
    id: number | null,
    name: string | null,
    price: number | null,
    active: boolean | null,
    stock: number | null,
    category: CategoryResponse | null
}

export interface CardResponse {
    id: number | null,
    user: UserResponse | null,
    principal: boolean | null,
    bin: string | null,
    last4: string | null,
    holder: string | null,
    expMonth: string | null,
    expYear: string | null,
}

export interface CategoryResponse {
    id: number | null,
    name: string | null,
    active: boolean | null
}

export interface GenderResponse {
    id: number | null,
    gender: string | null
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