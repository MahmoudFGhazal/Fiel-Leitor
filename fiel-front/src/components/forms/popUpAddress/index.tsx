'use client';
import { Address, ResidenceType, StreetType } from '@/api/objects';
import styles from './popUpAddress.module.css';
import InputText from '@/components/inputComponents/inputText';
import InputSelect from '@/components/inputComponents/inputSelect';
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from '@/translate/portuguses';
import { useEffect, useState } from 'react';
import { getResidenceTypes, getStreetTypes } from '@/utils/getTypes';

interface Props {
    address: Address;
    onChange: (field: keyof Address, value: any) => void;
    disable: boolean;
    streetTypes?: StreetType[];
    residenceTypes?: ResidenceType[];
}

export default function PopUpAddressCreate({ address, onChange, disable }: Props) {
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

    const handleChange = <K extends keyof Address>(field: K, value: Address[K]) => {
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
                />
                <InputText
                    type="text"
                    text="CEP"
                    value={address.zip || ""}
                    onChange={(val) => handleChange('zip', val)}
                    disabled={disable}
                />
            </div>

            <div className={styles.inputsContent}>
                <InputSelect
                    text="Tipo de Residência"
                    value={address.residenceType?.id?.toString() || ""}
                    onChange={(val: string) =>
                        handleChange('residenceType', residenceTypes?.find(r => r.id?.toString() === val) || null)
                    }
                    options={residenceTypes?.map(r => ({
                        value: r.id?.toString() || "",
                        label: TypeResidencesPortuguese[r.residenceType as keyof typeof TypeResidencesPortuguese],
                    })) || []}
                    disabled={disable}
                />

                <InputSelect
                    text="Tipo de Logradouro"
                    value={address.streetType?.id?.toString() || ""}
                    onChange={(val: string) =>
                        handleChange('streetType', streetTypes?.find(s => s.id?.toString() === val) || null)
                    }
                    options={streetTypes?.map(s => ({
                        value: s.id?.toString() || "",
                        label: TypeStreetsPortuguese[s.streetType as keyof typeof TypeStreetsPortuguese],
                    })) || []}
                    disabled={disable}
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Rua"
                    value={address.street || ""}
                    onChange={(val) => handleChange('street', val)}
                    disabled={disable}
                />
                <InputText
                    type="text"
                    text="Número"
                    value={address.number || ""}
                    onChange={(val) => handleChange('number', val)}
                    disabled={disable}
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Bairro"
                    value={address.neighborhood || ""}
                    onChange={(val) => handleChange('neighborhood', val)}
                    disabled={disable}
                />
                <InputText
                    type="text"
                    text="Cidade"
                    value={address.city || ""}
                    onChange={(val) => handleChange('city', val)}
                    disabled={disable}
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Estado"
                    value={address.state || ""}
                    onChange={(val) => handleChange('state', val)}
                    disabled={disable}
                />
                <InputText
                    type="text"
                    text="País"
                    value={address.country || ""}
                    onChange={(val) => handleChange('country', val)}
                    disabled={disable}
                />
            </div>

            <div className={styles.inputsContent}>
                <InputText
                    type="text"
                    text="Complemento"
                    value={address.complement || ""}
                    onChange={(val) => handleChange('complement', val)}
                    disabled={disable}
                />
            </div>
        </div>
    );
}
