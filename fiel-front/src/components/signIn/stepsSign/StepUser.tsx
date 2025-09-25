import InputText from '@/components/inputComponents/inputText';
import styles from '../signIn.module.css';

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
                dataCy='email-text'
            />

            <InputText
                type="password"
                text="Senha"
                value={formData.password}
                onChange={(val) => updateFormData({ password: val })}
                dataCy='password-text'
            />

            <InputText
                type="password"
                text="Confirmar Senha"
                value={confirmPassword}
                onChange={(val) => updateConfirmPassword(val)}
                dataCy='newPassword-text'
            />
        </div>
    );
}