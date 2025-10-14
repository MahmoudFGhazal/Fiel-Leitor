import { AddCartRequest, AddressRequest, CardRequest, CartRequest, GenderRequest, PromotionalCouponRequest, ResidenceTypeRequest, SaleBookRequest, SaleCardRequest, SaleRequest, SignRequest, StatusSaleRequest, StreetTypeRequest, TraderCouponRequest, UpdateCartRequest, UserRequest } from "./dtos/requestDTOs";
import { AddressResponse, CardResponse, CartResponse, GenderResponse, PromotionalCouponResponse, ResidenceTypeResponse, SaleBookResponse, SaleCardResponse, SaleResponse, StatusSaleResponse, StreetTypeResponse, TraderCouponResponse, UserResponse } from "./dtos/responseDTOs";

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
  | CartRequest
  | GenderRequest
  | PromotionalCouponRequest
  | ResidenceTypeRequest
  | SaleBookRequest
  | SaleCardRequest
  | SaleRequest
  | StatusSaleRequest
  | StreetTypeRequest
  | TraderCouponRequest
  | UserRequest
  | AddressRequest[]
  | CardRequest[]
  | CartRequest[]
  | GenderRequest[]
  | PromotionalCouponRequest[]
  | ResidenceTypeRequest[]
  | SaleBookRequest[]
  | SaleCardRequest[]
  | SaleRequest[]
  | StatusSaleRequest[]
  | StreetTypeRequest[]
  | TraderCouponRequest[]
  | UserRequest[]
  | SignRequest
  | UpdateCartRequest
  | AddCartRequest;

  
export type DatasResponde =
  | AddressResponse
  | CardResponse
  | CartResponse
  | GenderResponse
  | PromotionalCouponResponse
  | ResidenceTypeResponse
  | SaleBookResponse
  | SaleCardResponse
  | SaleResponse
  | StatusSaleResponse
  | StreetTypeResponse
  | TraderCouponResponse
  | UserResponse
  | AddressResponse[]
  | CardResponse[]
  | CartResponse[]
  | GenderResponse[]
  | PromotionalCouponResponse[]
  | ResidenceTypeResponse[]
  | SaleBookResponse[]
  | SaleCardResponse[]
  | SaleResponse[]
  | StatusSaleResponse[]
  | StreetTypeResponse[]
  | TraderCouponResponse[]
  | UserResponse[];
