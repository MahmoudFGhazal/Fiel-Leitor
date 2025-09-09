/* import { GendersPortuguese } from "@/modal/translate/portuguses";
import { Genders } from "@/modal/userModal";
import styles from '../signIn.module.css';
import Input from "@/components/input";

type StepProfileProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepProfile({ formData, updateFormData }: StepProfileProps) {
    return (
        <div className={styles.formStep}>
            <Input
                type='text'
                text="Nome"
                value={formData.name}
                onChange={(val) => updateFormData({ name: val })}
            />
            
            <Input
                text="Gênero"
                value={formData.gender || ""}
                onChange={(val) => updateFormData({ gender: val as Genders })}
                options={Object.entries(GendersPortuguese).map(([key, label]) => ({
                    value: key,
                    label,
                }))}
            />
            
            <Input
                type='date'
                text="Data de Nascimento"
                value={
                    formData.birthday
                        ? new Date(formData.birthday).toISOString().split("T")[0]
                        : ""
                }
                onChange={(val) => updateFormData({ birthday: val as string })}
            />

            <Input
                type='text'
                text="CPF"
                value={formData.cpf}
                onChange={(val) => updateFormData({ cpf: val })}
            />

            <Input
                type='text'
                text="Telefone"
                value={formData.phoneNumber}
                onChange={(val) => updateFormData({ phoneNumber: val })}
            />
        </div>
    );
} */