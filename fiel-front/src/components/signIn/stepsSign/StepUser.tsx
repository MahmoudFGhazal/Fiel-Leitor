import styles from '../signIn.module.css';
import InputText from '@/components/inputs/inputText';

type StepUserProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepUser({ formData, updateFormData }: StepUserProps) {
    let oi: string = '';
    
    return (
        <div className={styles.formStep}>
            <InputText
                type="email"
                text="Email"
                value={formData.email}
                onChange={(val) => updateFormData({ email: val })}
            />

            <InputText
                type="password"
                text="Senha"
                value={formData.password}
                onChange={(val) => updateFormData({ password: val })}
            />

            <InputText
                type="password"
                text="Confirmar Senha"
                value={oi}
                onChange={() => {}}
            />
        </div>
    );
}