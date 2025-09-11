
import { GendersPortuguese } from '@/translate/portuguses';
import styles from '../signIn.module.css';
import { Genders } from '@/translate/base';
import InputText from '@/components/inputs/inputText';
import { useEffect, useState } from 'react';
import { Gender } from '@/api/objects';
import { getGenders } from '@/utils/getTypes';
import InputSelect from '@/components/inputs/inputSelect';

type StepProfileProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepProfile({ formData, updateFormData }: StepProfileProps) {
    const [genderTypes, setGenderTypes] = useState<Gender[] | null>(null);
    
    useEffect(() => {
        async function fetchData() {
            try {
                const genders: Gender[] = await getGenders();

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
            />
            
            <InputSelect
                text="Gênero"
                value={formData.gender?.id?.toString() || ""}
                onChange={(val: string) => {
                    const selectedGender = genderTypes?.find(g => g.id?.toString() === val) || null;
                    updateFormData({ gender: selectedGender });
                }}
                options={
                    genderTypes?.map(g => ({
                        value: g.id?.toString() || "",
                        label: GendersPortuguese[g.gender as keyof typeof GendersPortuguese],
                    })) || []
                }
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
            />

            <InputText
                type='text'
                text="CPF"
                value={formData.cpf}
                onChange={(val) => updateFormData({ cpf: val })}
            />

            <InputText
                type='text'
                text="Telefone"
                value={formData.phoneNumber}
                onChange={(val) => updateFormData({ phoneNumber: val })}
            />
        </div>
    );
} 