import { AddressRequest, CardRequest, GenderRequest, PaymentTypeRequest, ResidenceTypeRequest, StreetTypeRequest, UserRequest } from "./dtos/requestDTOs";
import { AddressResponse, CardResponse, GenderResponse, PaymentTypeResponse, ResidenceTypeResponse, StreetTypeResponse, UserResponse } from "./dtos/responseDTOs";

export interface ApiResponse {
    data: {
        entity: DatasResponde | null,
        entities: DatasResponde | null,
        page: number | null,
        limit: number | null,
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

export type DatasRequest =
  | AddressRequest
  | CardRequest
  | GenderRequest
  | PaymentTypeRequest
  | ResidenceTypeRequest
  | StreetTypeRequest
  | UserRequest
  | AddressRequest[]
  | CardRequest[]
  | GenderRequest[]
  | PaymentTypeRequest[]
  | ResidenceTypeRequest[]
  | StreetTypeRequest[]
  | UserRequest[];

  
export type DatasResponde =
  | AddressResponse
  | CardResponse
  | GenderResponse
  | PaymentTypeResponse
  | ResidenceTypeResponse
  | StreetTypeResponse
  | UserResponse
  | AddressResponse[]
  | CardResponse[]
  | GenderResponse[]
  | PaymentTypeResponse[]
  | ResidenceTypeResponse[]
  | StreetTypeResponse[]
  | UserResponse[];
