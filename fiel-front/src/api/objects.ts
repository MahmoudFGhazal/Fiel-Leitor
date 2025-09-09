export interface Address {
    id: number | null,
    user: User | null,
    nickname: string | null,
    number: string | null,
    complement: string | null,
    street: string | null,
    neighborhood: string | null,
    zip: string | null,
    city: string | null,
    state: string | null,
    country: string | null,
    streetType: StreetType | null,
    residenceType: ResidenceType | null
}

export interface Card {
    id: number | null,
    user: User | null,
    principal: boolean | null,
    number: string | null,
    ccv: string | null,
    holder: string | null,
    valid: string | null,
    paymentType: PaymentType | null
}

export interface Gender {
    id: number | null,
    gender: string | null
}

export interface PaymentType {
    id: number | null,
    paymentType: string | null
}

export interface ResidenceType {
    id: number | null,
    residenceType: string | null
}

export interface StreetType {
    id: number | null,
    streetType: string | null
}

export interface User {
    id: number | null,
    email: string | null,
    password: string | null,
    name: string | null,
    active: boolean | null,
    gender: Gender | null,
    birthday: Date | null,
    cpf: string | null,
    phoneNumber: string | null
}

export interface ApiResponse {
    data: {
        entity: Datas | null,
        entities: Datas | null,
        page: number | null,
        limit: number | null,
        pageCount: number | null,
        totalItem: number | null,
        totalPage: number | null
    },
    typeResponse: TypeResponse | null,
    message: string | null
}

export enum TypeResponse {
    OK = 200,
    CLIENT_ERROR = 400,
    CONFLICT = 409,
    SERVER_ERROR = 500,
}

export type Datas =
  | Address
  | Card
  | Gender
  | PaymentType
  | ResidenceType
  | StreetType
  | User
  | Address[]
  | Card[]
  | Gender[]
  | PaymentType[]
  | ResidenceType[]
  | StreetType[]
  | User[];
