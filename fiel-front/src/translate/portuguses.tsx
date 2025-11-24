import { Categories, Genders, StatusSale, TypeResidences, TypeStreets } from "./base";

export const TypeResidencesPortuguese: Record<TypeResidences, string> = {
    [TypeResidences.HOUSE]: 'Casa',
    [TypeResidences.APARTMENT]: 'Apartamento',
    [TypeResidences.TOWNHOUSE]: 'Casa geminada',
    [TypeResidences.STUDIO]: 'Estúdio',
    [TypeResidences.FARMHOUSE]: 'Fazenda'
}

export const TypeStreetsPortuguese: Record<TypeStreets, string> = {
    [TypeStreets.AVENUE]: 'Avenida',
    [TypeStreets.STREET]: 'Rua',
    [TypeStreets.ALLEY]: 'Beco',
    [TypeStreets.HIGHWAY]: 'Rodovia',
    [TypeStreets.SQUARE]: 'Praça',
    [TypeStreets.BOULEVARD]: 'Boulevard'
}

export const GendersPortuguese: Record<Genders, string> = {
    [Genders.MALE]: 'Masculino',
    [Genders.FEMALE]: 'Feminino',
    [Genders.OTHER]: 'Outro',
    [Genders.PREFERNOTTOSAY]: 'Prefiro não informar'
}

export const CategoriesPortuguese: Record<Categories, string> = {
    [Categories.GENERAL_MEDICINE]: 'Medicina Geral',
    [Categories.PHARMACOLOGY]: 'Farmacologia',
    [Categories.ANATOMY]: 'Anatomia',
    [Categories.PHYSIOLOGY]: 'Fisiologia',
    [Categories.PATHOLOGY]: 'Patologia',
    [Categories.MICROBIOLOGY]: 'Microbiologia',
    [Categories.PEDIATRICS]: 'Pediatria',
    [Categories.GYNECOLOGY_OBSTETRICS]: 'Ginecologia e Obstetrícia',
    [Categories.GENERAL_SURGERY]: 'Cirurgia Geral',
    [Categories.PSYCHIATRY]: 'Psiquiatria',
};

export const StatusSalePortuguese: Record<StatusSale, string> = {
    [StatusSale.PROCESSING]: 'Em processamento',
    [StatusSale.APPROVED]: 'Aprovado',
    [StatusSale.DECLINED]: 'Recusado',
    [StatusSale.IN_TRANSIT]: 'Em trânsito',
    [StatusSale.DELIVERED]: 'Entregue',
    [StatusSale.EXCHANGE_REQUESTED]: 'Troca solicitada',
    [StatusSale.EXCHANGE_AUTHORIZED]: 'Troca autorizada',
    [StatusSale.EXCHANGED]: 'Trocado',
};
