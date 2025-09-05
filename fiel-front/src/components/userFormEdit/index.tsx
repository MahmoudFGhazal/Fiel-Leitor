'use client'
import { Genders, UserData } from '@/modal/userModal';
import { GendersPortuguese } from '@/modal/translate/portuguses';
import styles from './userFormEdit.module.css';

interface UserFormProps {
    user: UserData;
    disable: boolean;
    onChange: (field: string, value: string) => void;
}

export default function UserForm({ user, disable, onChange }: UserFormProps) {
    const formatCPF = (cpf: string) => {
        return cpf.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, "$1.$2.$3-$4");
    };

    const formatPhone = (phone: string) => {
        return phone.replace(/^(\d{2})(\d{5})(\d{4})$/, "($1) $2-$3");
    };

    return (
        <div className={styles.formContainer}>
            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <strong>E-mail:</strong> 
                    <input
                        type="text"
                        value={user.email}
                        disabled={disable}
                        onChange={(e) => onChange("email", e.target.value)}
                    />
                </div>
                <div className={styles.inputContent}>
                    <strong>Senha:</strong> 
                    <input
                        type="text"
                        value={user.password}
                        disabled={disable}
                        onChange={(e) => onChange("password", e.target.value)}
                    />
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <strong>Nome:</strong> 
                    <input
                        type="text"
                        value={user.name}
                        disabled={disable}
                        onChange={(e) => onChange("name", e.target.value)}
                    />
                </div>
                <div className={styles.inputContent}>
                    <strong>Gênero:</strong> 
                    <select
                        value={user.gender ?? ""}
                        disabled={disable}
                        onChange={(e) => onChange("gender", e.target.value as Genders)}
                    >
                        {Object.entries(GendersPortuguese).map(([key, label]) => (
                            <option key={key} value={key}>
                                {label}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <strong>Data de Nascimento:</strong> 
                    <input
                        type="date"
                        value={
                            user.birthday
                                ? new Date(user.birthday).toISOString().split("T")[0]
                                : ""
                        }
                        disabled={disable}
                        onChange={(e) => onChange("birthday", e.target.value)}
                    />
                </div>
                <div className={styles.inputContent}>
                    <strong>CPF:</strong> 
                    <input
                        type="text"
                        value={disable ? formatCPF(user.cpf) : user.cpf}
                        disabled={disable}
                        onChange={(e) => onChange("cpf", e.target.value)}
                    />
                </div>
            </div>

            <div className={styles.inputContent}>
                <strong>Número de Telefone:</strong> 
                <input
                    type="text"
                    value={disable ? formatPhone(user.phoneNumber) : user.phoneNumber}
                    disabled={disable}
                    onChange={(e) => onChange("phoneNumber", e.target.value)}
                />
            </div>
        </div>
    );
}
