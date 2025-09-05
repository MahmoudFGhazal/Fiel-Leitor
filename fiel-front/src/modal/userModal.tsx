import { AddressData } from "./addressModal";

export interface UserData {
    id: number,
    email: string,
    password: string,
    name: string,
    gender: Genders | null,
    birthday: Date | null,
    cpf: string,
    phoneNumber: string,
    addresses: AddressData[],
    cards: string[],
    isActive: boolean
}

export type LoginData = {
    email: string;
    password: string;
    refresh: boolean;
};

export enum Genders {
    MALE = 'male',
    FEMALE = 'female',
    OTHER = 'other',
    NOTSHARE = 'notShare'
}