export interface Address {
    id: number,
    user: User,
    nickname: string,
    number: string,
    complement: string,
    street: string,
    neighborhood: string,
    zip: string,
    city: string,
    state: string,
    country: string,
    streetType: StreetType,
    residenceType: ResidenceType
}

export interface Card {
    id: number,
    user: User,
    principal: boolean,
    number: string,
    ccv: string,
    holder: string,
    valid: string,
    paymentType: PaymentType
}

export interface Gender {
    id: number,
    gender: string
}

export interface PaymentType {
    id: number,
    paymentType: string
}

export interface ResidenceType {
    id: number,
    residenceType: string
}

export interface StreetType {
    id: number,
    streetType: string
}

export interface User {
    id: number,
    email: string,
    password: string,
    name: string,
    active: boolean,
    gender: Gender,
    birthday: Date,
    cpf: string,
    phoneNumber: string
}

export interface ApiResponse {
    data: {
        entity: Datas,
        entities: Datas,
        page: number,
        limit: number,
        pageCount: number,
        totalItem: number,
        totalPage: number
    },
    typeResponse: TypeResponse,
    message: string
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