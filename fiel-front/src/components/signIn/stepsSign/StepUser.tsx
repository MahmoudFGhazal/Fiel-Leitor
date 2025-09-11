import styles from '../signIn.module.css';
import InputText from '@/components/inputs/inputText';

type StepUserProps = {
    formData: any;
    confirmPassword: string;
    updateFormData: (data: any) => void;
    updateConfirmPassword: (data: string) => void;
};

export default function StepUser({ formData, confirmPassword, updateFormData, updateConfirmPassword }: StepUserProps) {
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
                value={confirmPassword}
                onChange={(val) => updateConfirmPassword(val)}
            />
        </div>
    );
}