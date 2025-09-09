'use client'
import { Address, ResidenceType, StreetType } from '@/api/objects';
import styles from '../signIn.module.css';
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from '@/translate/portuguses';
import { useEffect, useState } from 'react';
import { getResidenceTypes, getStreetTypes } from '@/utils/getTypes';
import InputSelect from '@/components/inputs/inputSelect';
import InputText from '@/components/inputs/inputText';

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

    const currentAddress = formData.addresses?.[0] as Address | undefined;
        
    const handleChange = <K extends keyof Address>(field: K, value: Address[K]) => {
        const updatedAddress = { ...currentAddress, [field]: value };
        updateFormData({ addresses: [updatedAddress] });
    };

    return (
        <div className={`${styles.formStep} ${styles.formAddress}`}>
            <InputText
                type='text'
                text="CEP"
                value={formData.addresses?.[0]?.zip || ""}
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
                value={formData.addresses?.[0]?.street || ""}
                onChange={(val) => handleChange('street', val)}
            />

            <InputText
                type='text'
                text="Número"
                value={formData.addresses?.[0]?.number || ""}
                onChange={(val) => handleChange('number', val)}
            />

            <InputText
                type='text'
                text="Bairro"
                value={formData.addresses?.[0]?.neighborhood || ""}
                onChange={(val) => handleChange('neighborhood', val)}
            />

            <InputText
                type='text'
                text="Cidade"
                value={formData.addresses?.[0]?.city || ""}
                onChange={(val) => handleChange('city', val)}
            />

            <InputText
                type='text'
                text="Estado"
                value={formData.addresses?.[0]?.state || ""}
                onChange={(val) => handleChange('state', val)}
            />

            <InputText
                type='text'
                text="País"
                value={formData.addresses?.[0]?.country || ""}
                onChange={(val) => handleChange('country', val)}
            />
        </div>
    );
}