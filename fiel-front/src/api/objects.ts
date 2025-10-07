import { AddressRequest, CardRequest, GenderRequest, ResidenceTypeRequest, SignRequest, StreetTypeRequest, UserRequest } from "./dtos/requestDTOs";
import { AddressResponse, CardResponse, GenderResponse, ResidenceTypeResponse, StreetTypeResponse, UserResponse } from "./dtos/responseDTOs";

export interface ApiResponse {
    data: {
        entity: DatasResponde | null,
        entities: DatasResponde | null,
        page: number | null,
        limit: number | null,
        totalItem: number | null,
        totalPage: number | null
    },
    typeResponse: TypeResponse,
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
  | ResidenceTypeRequest
  | StreetTypeRequest
  | UserRequest
  | AddressRequest[]
  | CardRequest[]
  | GenderRequest[]
  | ResidenceTypeRequest[]
  | StreetTypeRequest[]
  | UserRequest[]
  | SignRequest;

  
export type DatasResponde =
  | AddressResponse
  | CardResponse
  | GenderResponse
  | ResidenceTypeResponse
  | StreetTypeResponse
  | UserResponse
  | AddressResponse[]
  | CardResponse[]
  | GenderResponse[]
  | ResidenceTypeResponse[]
  | StreetTypeResponse[]
  | UserResponse[];
