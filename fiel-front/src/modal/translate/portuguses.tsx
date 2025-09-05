import { TypeResidences, TypeStreets } from "../addressModal"
import { Categories } from "../productModal"
import { Genders } from "../userModal"

export const TypeResidencesPortuguese: Record<TypeResidences, string> = {
    [TypeResidences.HOUSE]: 'Casa',
    [TypeResidences.APARTMENT]: 'Apartamento',
    [TypeResidences.CONDOMINIUM]: 'Condomínio'
}

export const TypeStreetsPortuguese: Record<TypeStreets, string> = {
    [TypeStreets.AVENUE]: 'Avenida',
    [TypeStreets.STREET]: 'Rua',
    [TypeStreets.ROAD]: 'Rodovia',
    [TypeStreets.HIGHWAY]: 'Estrada'
}

export const GendersPortuguese: Record<Genders, string> = {
    [Genders.MALE]: 'Masculino',
    [Genders.FEMALE]: 'Feminino',
    [Genders.OTHER]: 'Outro',
    [Genders.NOTSHARE]: 'Prefiro não compartilhar'
}

export const CategoriesPortuguese: Record<Categories, string> = {
    [Categories.SUSPENSE]: 'Suspense',
    [Categories.COMEDY]: 'Comedia',
    [Categories.ADVENTURE]: 'Aventura',
    [Categories.ROMANCE]: 'Romance'
}