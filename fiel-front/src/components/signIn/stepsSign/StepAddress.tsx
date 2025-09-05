import { AddressData, TypeResidences, TypeStreets } from "@/modal/addressModal";
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from "@/modal/translate/portuguses";
import { Genders, UserData } from "@/modal/userModal";
import styles from '../signIn.module.css';
import Input from "@/components/input";
import { useState } from "react";

type StepAddressProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepAddress({ formData, updateFormData }: StepAddressProps) {
    const handleChange = (field: keyof AddressData, value: string | null) => {
        const address = formData.addresses[0] || { id: Date.now() } as AddressData;

        const updatedAddress = { ...address, [field]: value };
        updateFormData({ addresses: [updatedAddress] });
    };

    return (
        <div className={`${styles.formStep} ${styles.formAddress}`}>
            <Input
                type='text'
                text="CEP"
                value={formData.addresses?.[0]?.zip || ""}
                onChange={(val) => handleChange('zip', val)}
            />

            <Input
                text="Tipo de Residência"
                value={formData.addresses?.[0]?.typeResidence || ""}
                onChange={(val) => handleChange('typeResidence', val)}
                options={Object.entries(TypeResidencesPortuguese).map(([key, label]) => ({
                    value: key,
                    label,
                }))}
            />

            <Input
                text="Tipo de Logradouro"
                value={formData.addresses?.[0]?.typeStreet || ""}
                onChange={(val) =>  handleChange('typeStreet', val)}
                options={Object.entries(TypeStreetsPortuguese).map(([key, label]) => ({
                    value: key,
                    label,
                }))}
            />
            
            <Input
                type='text'
                text="Rua"
                value={formData.addresses?.[0]?.street || ""}
                onChange={(val) => handleChange('street', val)}
            />

            <Input
                type='text'
                text="Número"
                value={formData.addresses?.[0]?.number || ""}
                onChange={(val) => handleChange('number', val)}
            />

            <Input
                type='text'
                text="Bairro"
                value={formData.addresses?.[0]?.neighborhood || ""}
                onChange={(val) => handleChange('neighborhood', val)}
            />

            <Input
                type='text'
                text="Cidade"
                value={formData.addresses?.[0]?.city || ""}
                onChange={(val) => handleChange('city', val)}
            />

            <Input
                type='text'
                text="Estado"
                value={formData.addresses?.[0]?.state || ""}
                onChange={(val) => handleChange('state', val)}
            />

            <Input
                type='text'
                text="País"
                value={formData.addresses?.[0]?.country || ""}
                onChange={(val) => handleChange('country', val)}
            />
        </div>
    );
}