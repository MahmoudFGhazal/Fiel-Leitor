'use client';
import { AddressRequest } from '@/api/dtos/requestDTOs';
import { ResidenceTypeResponse, StreetTypeResponse } from '@/api/dtos/responseDTOs';
import InputSelect from '@/components/inputComponents/inputSelect';
import InputText from '@/components/inputComponents/inputText';
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from '@/translate/portuguses';
import { getResidenceTypes, getStreetTypes } from '@/utils/getTypes';
import { useEffect, useState } from 'react';
import styles from './popUpAddress.module.css';

interface Props {
    address: AddressRequest;
    onChange: (field: keyof AddressRequest, value: any) => void;
    disable: boolean;
    streetTypes?: StreetTypeResponse[];
    residenceTypes?: ResidenceTypeResponse[];
}

export default function PopUpAddressCreate({ address, onChange, disable }: Props) {
    const [streetTypes, setStreetTypes] = useState<StreetTypeResponse[] | null>(null);
    const [residenceTypes, setResidenceTypes] = useState<ResidenceTypeResponse[] | null>(null);
    
    useEffect(() => {
        async function fetchData() {
            try {
                const streets: StreetTypeResponse[] = await getStreetTypes();
                const residences: ResidenceTypeResponse[] = await getResidenceTypes();

                setStreetTypes(streets);
                setResidenceTypes(residences);
            } catch (err) {
                console.error("Erro ao carregar dados", err);
            }
        }

        fetchData();
    }, []);

    const handleChange = <K extends keyof AddressRequest>(field: K, value: AddressRequest[K]) => {
        onChange(field, value);
    };

    return (
        <div className={styles.formContainer}>
            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Apelido do Endereço"
                    value={address.nickname || ""}
                    onChange={(val) => handleChange('nickname', val)}
                    disabled={disable}
                    dataCy='nickname-text'
                />
                <InputText
                    type="text"
                    text="CEP"
                    value={address.zip || ""}
                    onChange={(val) => handleChange('zip', val)}
                    disabled={disable}
                    dataCy='cep-text'
                />
            </div>

            <div className={styles.inputsContent}>
                <InputSelect
                    text="Tipo de Residência"
                    value={address.residenceType?.toString() || ""}
                    onChange={(val: string) =>
                        handleChange('residenceType', val ? Number(val) : null)
                    }
                    options={residenceTypes?.map(r => ({
                        value: r.id?.toString() || "",
                        label: TypeResidencesPortuguese[r.residenceType as keyof typeof TypeResidencesPortuguese],
                    })) || []}
                    disabled={disable}
                    dataCy='residence-select'
                />

                <InputSelect
                    text="Tipo de Logradouro"
                    value={address.streetType?.toString() || ""}
                    onChange={(val: string) =>
                        handleChange('streetType', val ? Number(val) : null)
                    }
                    options={streetTypes?.map(s => ({
                        value: s.id?.toString() || "",
                        label: TypeStreetsPortuguese[s.streetType as keyof typeof TypeStreetsPortuguese],
                    })) || []}
                    disabled={disable}
                    dataCy='street-select'
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Rua"
                    value={address.street || ""}
                    onChange={(val) => handleChange('street', val)}
                    disabled={disable}
                    dataCy='street-text'
                />
                <InputText
                    type="text"
                    text="Número"
                    value={address.number || ""}
                    onChange={(val) => handleChange('number', val)}
                    disabled={disable}
                    dataCy='number-text'
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Bairro"
                    value={address.neighborhood || ""}
                    onChange={(val) => handleChange('neighborhood', val)}
                    disabled={disable}
                    dataCy='neighbor-text'
                />
                <InputText
                    type="text"
                    text="Cidade"
                    value={address.city || ""}
                    onChange={(val) => handleChange('city', val)}
                    disabled={disable}
                    dataCy='city-text'
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Estado"
                    value={address.state || ""}
                    onChange={(val) => handleChange('state', val)}
                    disabled={disable}
                    dataCy='state-text'
                />
                <InputText
                    type="text"
                    text="País"
                    value={address.country || ""}
                    onChange={(val) => handleChange('country', val)}
                    disabled={disable}
                    dataCy='country-text'
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Complemento"
                    value={address.complement || ""}
                    onChange={(val) => handleChange('complement', val)}
                    disabled={disable}
                    dataCy='complement-text'
                />
            </div>
        </div>
    );
}
