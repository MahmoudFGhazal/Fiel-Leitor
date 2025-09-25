
import { GenderResponse } from '@/api/dtos/responseDTOs';
import InputSelect from '@/components/inputComponents/inputSelect';
import InputText from '@/components/inputComponents/inputText';
import { GendersPortuguese } from '@/translate/portuguses';
import { getGenders } from '@/utils/getTypes';
import { useEffect, useState } from 'react';
import styles from '../signIn.module.css';

type StepProfileProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepProfile({ formData, updateFormData }: StepProfileProps) {
    const [genderTypes, setGenderTypes] = useState<GenderResponse[] | null>(null);
    
    useEffect(() => {
        async function fetchData() {
            try {
                const genders: GenderResponse[] = await getGenders();

                setGenderTypes(genders);
            } catch (err) {
                console.error("Erro ao carregar dados", err);
            }
        }

        fetchData();
    }, []);
    
    return (
        <div className={styles.formStep}>
            <InputText
                type='text'
                text="Nome"
                value={formData.name}
                onChange={(val) => updateFormData({ name: val })}
                dataCy='name-text'
            />
            
            <InputSelect
                text="Gênero"
                value={formData.gender?.toString() || ""}
                onChange={(val: string) => {
                    updateFormData({ gender: val ? parseInt(val) : null });
                }}
                options={
                    genderTypes?.map(g => ({
                        value: g.id?.toString() || "",
                        label: GendersPortuguese[g.gender as keyof typeof GendersPortuguese],
                    })) || []
                }
                dataCy='gender-select'
            />
            
            <InputText
                type='date'
                text="Data de Nascimento"
                value={
                    formData.birthday
                        ? new Date(formData.birthday).toISOString().split("T")[0]
                        : ""
                }
                onChange={(val) => updateFormData({ birthday: val as string })}
                dataCy='birth-date'
            />

            <InputText
                type='text'
                text="CPF"
                value={formData.cpf}
                onChange={(val) => updateFormData({ cpf: val })}
                dataCy='cpf-text'
            />

            <InputText
                type='text'
                text="Telefone"
                value={formData.phoneNumber}
                onChange={(val) => updateFormData({ phoneNumber: val })}
                dataCy='phone-text'
            />
        </div>
    );
} 