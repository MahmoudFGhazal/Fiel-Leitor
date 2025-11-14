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
    id: number;

    name: string;
    author: string;
    publisher: string;
    edition: string;
    year: number;

    isbn: string;
    barcode: string;
    synopsis: string;

    pages: number;

    height: number | null;
    width: number | null;
    depth: number | null;
    weight: number | null;

    price: number;
    stock: number;
    active: boolean;

    priceGroup: PriceGroupResponse;
    categories: CategoryResponse[];
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

export interface CartResponse {
    user: UserResponse | null,   
    book: BookResponse | null,   
    quantity: number | null
}

export interface CategoryResponse {
    id: number | null,
    category: string | null,
    active: boolean | null
}

export interface GenderResponse {
    id: number | null,
    gender: string | null
}

export interface PriceGroupResponse {
    id: number;
    name: string;
    marginPct: number;
    active: boolean;
}

export interface PromotionalCouponResponse {
    id: number | null,
    code: string | null,
    value: number | null,
    used: boolean | null      
}

export interface ResidenceTypeResponse {
    id: number | null,
    residenceType: string | null
}

export interface SaleBookResponse {
    sale: SaleResponse | null,    
    book: BookResponse | null,   
    price: number | null,
    quantity: number | null
}

export interface SaleCardResponse {
    sale: SaleResponse | null,   
    card: number | null, 
    percent: number | null,
}

export interface SaleResponse {
    id: number | null,
    user: UserResponse | null,                   
    freight: number | null,
    deliveryDate: Date | null,
    statusSale: StatusSaleResponse | null,           
    address: AddressResponse | null,           
    cards: SaleCardResponse[] | null,            
    saleBooks: SaleBookResponse[] | null,            
    traderCoupon: TraderCouponResponse | null,    
    promotinalCoupons: PromotionalCouponResponse[] | null,
    createdAt: Date | null
}

export interface StatusSaleResponse {
    id: number | null,
    status: string | null  
}

export interface StreetTypeResponse {
    id: number | null,
    streetType: string | null
}

export interface TraderCouponResponse {
    id: number | null,
    code: string | null,
    value: number | null,
    used: boolean | null      
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