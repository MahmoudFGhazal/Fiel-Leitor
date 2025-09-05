import Input from '@/components/input';
import styles from '../signIn.module.css';

type StepUserProps = {
    formData: any;
    updateFormData: (data: any) => void;
};

export default function StepUser({ formData, updateFormData }: StepUserProps) {
    let oi: string = '';
    
    return (
        <div className={styles.formStep}>
            <Input
                type="email"
                text="Email"
                value={formData.email}
                onChange={(val) => updateFormData({ email: val })}
            />

            <Input
                type="password"
                text="Senha"
                value={formData.password}
                onChange={(val) => updateFormData({ password: val })}
            />

            <Input
                type="password"
                text="Confirmar Senha"
                value={oi}
                onChange={() => {}}
            />
        </div>
    );
}