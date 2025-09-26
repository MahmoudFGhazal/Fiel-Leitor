'use client'
import { UserRequest } from '@/api/dtos/requestDTOs';
import { GenderResponse } from '@/api/dtos/responseDTOs';
import { GendersPortuguese } from '@/translate/portuguses';
import { getGenders } from '@/utils/getTypes';
import { useEffect, useState } from 'react';
import InputSelect from '../../inputComponents/inputSelect';
import InputText from '../../inputComponents/inputText';
import styles from './userFormEdit.module.css';

interface UserFormProps {
    user: UserRequest;
    disable: boolean;
    onChange: (field: string, value: string) => void;
}

export default function UserForm({ user, disable, onChange }: UserFormProps) {
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
        <div className={styles.formContainer}>
            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="E-mail"
                        disabled={disable}
                        value={user.email ?? ""}
                        onChange={(value) => onChange("email", value)}
                        dataCy='email-text'
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="Nome"
                        disabled={disable}
                        value={user.name ?? ""}
                        onChange={(value) => onChange("name", value)}
                        dataCy='name-text'
                    />
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputSelect
                        text="Gênero"
                        disabled={disable}
                        value={user.gender?.toString() ?? ""}
                        onChange={(value) => onChange("gender", value)}
                        options={
                            genderTypes?.map(gender => ({
                                value: gender.id?.toString() || "",
                                label: GendersPortuguese[gender.gender as keyof typeof GendersPortuguese],
                            })) || []
                        }
                        dataCy='gender-select'
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="Número de Telefone"
                        disabled={disable}
                        value={user.phoneNumber ?? ""}
                        onChange={(value) => onChange("phoneNumber", value)}
                        dataCy='phonuNumber-text'
                    />
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputText
                        type="date"
                        text="Data de Nascimento"
                        disabled={disable}
                        value={
                            user.birthday
                                ? new Date(user.birthday).toISOString().split("T")[0]
                                : ""
                        }
                        onChange={(value) => onChange("birthday", value)}
                        dataCy='birth-date'
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="CPF"
                        disabled={disable}
                        value={user.cpf ?? ""}
                        onChange={(value) => onChange("cpf", value)}
                        dataCy='cpf-text'
                    />
                </div>
            </div>
        </div>
    );
}