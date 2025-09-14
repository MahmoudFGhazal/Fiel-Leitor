'use client'
import { Address, ResidenceType, StreetType } from '@/api/objects';
import styles from '../signIn.module.css';
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from '@/translate/portuguses';
import { useEffect, useState } from 'react';
import { getResidenceTypes, getStreetTypes } from '@/utils/getTypes';
import InputSelect from '@/components/inputComponents/inputSelect';
import InputText from '@/components/inputComponents/inputText';

type StepAddressProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepAddress({ formData, updateFormData }: StepAddressProps) {
    const [streetTypes, setStreetTypes] = useState<StreetType[] | null>(null);
    const [residenceTypes, setResidenceTypes] = useState<ResidenceType[] | null>(null);
    
    useEffect(() => {
        async function fetchData() {
            try {
                const streets: StreetType[] = await getStreetTypes();
                const residences: ResidenceType[] = await getResidenceTypes();

                setStreetTypes(streets);
                setResidenceTypes(residences);
            } catch (err) {
                console.error("Erro ao carregar dados", err);
            }
        }

        fetchData();
    }, []);

    const currentAddress = formData as Address;

    const handleChange = <K extends keyof Address>(field: K, value: Address[K]) => {
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
            />
            
            <InputText
                type='text'
                text="CEP"
                value={currentAddress?.zip || ""}
                onChange={(val) => handleChange('zip', val)}
            />

            <InputSelect
                text="Tipo de Residência"
                value={currentAddress?.residenceType?.id?.toString() || ""}
                onChange={(val: string) =>
                    handleChange("residenceType", residenceTypes?.find(r => r.id?.toString() === val) || null)
                }
                options={residenceTypes?.map(res => ({
                    value: res.id?.toString() || "",
                    label: TypeResidencesPortuguese[res.residenceType as keyof typeof TypeResidencesPortuguese],
                })) || []}
            />

            <InputSelect
                text="Tipo de Logradouro"
                value={currentAddress?.streetType?.id?.toString() || ""}
                onChange={(val: string) =>
                    handleChange("streetType", streetTypes?.find(s => s.id?.toString() === val) || null)
                }
                options={streetTypes?.map(street => ({
                    value: street.id?.toString() || "",
                    label: TypeStreetsPortuguese[street.streetType as keyof typeof TypeStreetsPortuguese],
                })) || []}
            />
            
            <InputText
                type='text'
                text="Rua"
                value={currentAddress?.street || ""}
                onChange={(val) => handleChange('street', val)}
            />

            <InputText
                type='text'
                text="Número"
                value={currentAddress?.number || ""}
                onChange={(val) => handleChange('number', val)}
            />

            <InputText
                type='text'
                text="Bairro"
                value={currentAddress?.neighborhood || ""}
                onChange={(val) => handleChange('neighborhood', val)}
            />

            <InputText
                type='text'
                text="Cidade"
                value={currentAddress?.city || ""}
                onChange={(val) => handleChange('city', val)}
            />

            <InputText
                type='text'
                text="Estado"
                value={currentAddress?.state || ""}
                onChange={(val) => handleChange('state', val)}
            />

            <InputText
                type='text'
                text="País"
                value={currentAddress?.country || ""}
                onChange={(val) => handleChange('country', val)}
            />

            <InputText
                type='text'
                text="Complemento"
                value={currentAddress?.complement || ""}
                onChange={(val) => handleChange('complement', val)}
            />
        </div>
    );
}