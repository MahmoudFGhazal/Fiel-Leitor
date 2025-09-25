'use client'
import { AddressRequest } from '@/api/dtos/requestDTOs';
import { ResidenceTypeResponse, StreetTypeResponse } from '@/api/dtos/responseDTOs';
import InputSelect from '@/components/inputComponents/inputSelect';
import InputText from '@/components/inputComponents/inputText';
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from '@/translate/portuguses';
import { getResidenceTypes, getStreetTypes } from '@/utils/getTypes';
import { useEffect, useState } from 'react';
import styles from '../signIn.module.css';

type StepAddressProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepAddress({ formData, updateFormData }: StepAddressProps) {
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

    const currentAddress = formData as AddressRequest;

    const handleChange = <K extends keyof AddressRequest>(field: K, value: AddressRequest[K]) => {
        const updatedAddress = { ...currentAddress, [field]: value };
        updateFormData(updatedAddress);
    };

    return (
        <div className={`${styles.formStep} ${styles.formAddress}`}>
            <InputText
                type='text'
                text="Apelido do Endereço"
                value={currentAddress?.nickname || ""}
                onChange={(val) => handleChange('nickname', val)}
                dataCy='nickname-text'
            />
            
            <InputText
                type='text'
                text="CEP"
                value={currentAddress?.zip || ""}
                onChange={(val) => handleChange('zip', val)}
                dataCy='cep-text'
            />

            <InputSelect
                text="Tipo de Residência"
                value={currentAddress?.residenceType?.toString() || ""}
                onChange={(val: string) =>
                    handleChange("residenceType", val ? parseInt(val) : null)
                }
                options={residenceTypes?.map(res => ({
                    value: res.id?.toString() || "",
                    label: TypeResidencesPortuguese[res.residenceType as keyof typeof TypeResidencesPortuguese],
                })) || []}
                dataCy='residence-select'
            />

            <InputSelect
                text="Tipo de Logradouro"
                value={currentAddress?.streetType?.toString() || ""}
                onChange={(val: string) =>
                    handleChange("streetType", val ? parseInt(val) : null)
                }
                options={streetTypes?.map(street => ({
                    value: street.id?.toString() || "",
                    label: TypeStreetsPortuguese[street.streetType as keyof typeof TypeStreetsPortuguese],
                })) || []}
                dataCy='street-select'
            />
            
            <InputText
                type='text'
                text="Rua"
                value={currentAddress?.street || ""}
                onChange={(val) => handleChange('street', val)}
                dataCy='street-text'
            />

            <InputText
                type='text'
                text="Número"
                value={currentAddress?.number || ""}
                onChange={(val) => handleChange('number', val)}
                dataCy='number-text'
            />

            <InputText
                type='text'
                text="Bairro"
                value={currentAddress?.neighborhood || ""}
                onChange={(val) => handleChange('neighborhood', val)}
                dataCy='neighbor-text'
            />

            <InputText
                type='text'
                text="Cidade"
                value={currentAddress?.city || ""}
                onChange={(val) => handleChange('city', val)}
                dataCy='city-text'
            />

            <InputText
                type='text'
                text="Estado"
                value={currentAddress?.state || ""}
                onChange={(val) => handleChange('state', val)}
                dataCy='state-text'
            />

            <InputText
                type='text'
                text="País"
                value={currentAddress?.country || ""}
                onChange={(val) => handleChange('country', val)}
                dataCy='country-text'
            />

            <InputText
                type='text'
                text="Complemento"
                value={currentAddress?.complement || ""}
                onChange={(val) => handleChange('complement', val)}
                dataCy='complement-text'
            />
        </div>
    );
}