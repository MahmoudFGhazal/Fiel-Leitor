'use client'
import { GendersPortuguese } from '@/translate/portuguses';
import styles from './userFormEdit.module.css';
import { Genders } from '@/translate/base';
import { Gender, User } from '@/api/objects';
import InputText from '../../inputComponents/inputText';
import InputSelect from '../../inputComponents/inputSelect';
import { useEffect, useState } from 'react';
import { getGenders } from '@/utils/getTypes';

interface UserFormProps {
    user: User;
    disable: boolean;
    onChange: (field: string, value: string) => void;
}

export default function UserForm({ user, disable, onChange }: UserFormProps) {
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
        <div className={styles.formContainer}>
            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="E-mail"
                        disabled={disable}
                        value={user.email ?? ""}
                        onChange={(value) => onChange("email", value)}
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputText
                        type="password"
                        text="Senha"
                        disabled={disable}
                        value={user.password ?? ""}
                        onChange={(value) => onChange("password", value)}
                    />
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="Nome"
                        disabled={disable}
                        value={user.name ?? ""}
                        onChange={(value) => onChange("name", value)}
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputSelect
                        text="Gênero"
                        disabled={disable}
                        value={user.gender?.id?.toString() ?? ""}
                        onChange={(value) => onChange("gender", value)}
                        options={
                            genderTypes?.map(gender => ({
                                value: gender.id?.toString() || "",
                                label: GendersPortuguese[gender.gender as keyof typeof GendersPortuguese],
                            })) || []
                        }
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
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputText
                        type="text"
                        text="CPF"
                        disabled={disable}
                        value={user.cpf ?? ""}
                        onChange={(value) => onChange("cpf", value)}
                    />
                </div>
            </div>

            <div className={styles.inputContent}>
                <InputText
                    type="text"
                    text="Número de Telefone"
                    disabled={disable}
                    value={user.phoneNumber ?? ""}
                    onChange={(value) => onChange("phoneNumber", value)}
                />
            </div>
        </div>
    );
}