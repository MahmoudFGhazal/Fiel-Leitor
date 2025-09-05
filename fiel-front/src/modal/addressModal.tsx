
export interface AddressData {
    id: number,
    typeResidence: TypeResidences | null,
    typeStreet: TypeStreets | null,
    street: string,
    number: string,
    neighborhood: string,
    zip: string,
    city: string,
    state: string,
    country: string,
    complement: string
}

export enum TypeResidences {
    HOUSE = 'house',
    APARTMENT = 'apartment',
    CONDOMINIUM = 'condominium'
}

export enum TypeStreets {
    AVENUE = 'avenue',
    STREET = 'street',
    ROAD = 'road',
    HIGHWAY = 'highway'
}