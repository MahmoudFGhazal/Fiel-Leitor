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
    category: number | null
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

export interface CartRequest {
    user: number | null,
    book: number | null,
    quantity: number | null
}

export interface CategoryRequest {
    id: number | null,
    category: string | null,
    active: boolean | null
}

export interface GenderRequest {
    id: number | null,
    gender: string | null
}

export interface PromotionalCouponRequest {
    id: number | null,
    value: number | null,
    used: boolean | null
}

export interface ResidenceTypeRequest {
    id: number | null,
    residenceType: string | null
}

export interface SaleBookRequest {
    sale: number | null,
    book: number | null,
    quantity: number | null,
    price: Date | null,
}

export interface SaleCardRequest {
    sale: number | null,
    card: number | null,
    percent: number | null,
    price: number | null,
}

export interface SaleRequest {
    id: number | null,
    user: number | null,
    freight: number | null,
    deliveryDate: Date | null,
    status: string | null,
    address: number | null,
    cards: SaleCardRequest[] | null,
    books: SaleBookRequest[] | null,
    traderCoupons: number[] | null,
    promotinalCoupon: number | null
}

export interface StatusSaleRequest {
    id: number | null,
    name: string | null
}

export interface StreetTypeRequest {
    id: number | null,
    streetType: string | null
}

export interface TraderCouponRequest {
    id: number | null,
    originSale: number | null,
    appliedSale: number | null,
    user: number | null,
    value: number | null,
    user: boolean | null,
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

export interface UpdateCartRequest {
    userId: number,
    addIds: CartRequest[],
    updateIds: CartRequest[],
    deleteIds: CartRequest[],
}

export interface AddCartRequest {
    userId: number,
    addIds: CartRequest[]
}
