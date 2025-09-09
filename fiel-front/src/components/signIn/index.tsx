'use client'
import styles from './signIn.module.css';
import { useState } from "react";
import StepUser from "./stepsSign/StepUser";
import StepAddress from "./stepsSign/StepAddress";
import StepProfile from "./stepsSign/StepProfile";
import { UserData } from '@/modal/userModal';
import BoxInput from '../formBox';
import Button from '../button';

export default function SignInComponent() {
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState<UserData>({
        id: Date.now(),
        email: "",
        password: "",
        name: "",
        gender: null,
        birthday: null,
        cpf: "",
        phoneNumber: "",
        addresses: [],
        cards: [],
        isActive: true
    });

    const nextStep = () => {
        if (step < 3) {
            setStep(step + 1);
        } else {
            saveUser();
        }
    };

    const showToast = (msg: string) => {
        return new Promise<void>((resolve) => {
            alert(msg); 
            resolve();
        });
    };

    const saveUser = async () => {
        const users = JSON.parse(localStorage.getItem('users') || '[]');
        users.push(formData);
        localStorage.setItem('users', JSON.stringify(users));
        await showToast('Cadastro concluído com sucesso!');
        window.location.href = "/login";
    };

    const prevStep = () => setStep(step - 1);

    const updateFormData = (data: any) => {
        setFormData({ ...formData, ...data });
    };

    return (
        <BoxInput>
            <div className={styles.formContainer}>
                {step === 1 && 
                    <StepUser 
                        formData={formData}
                        updateFormData={updateFormData}
                    />
                }
                {step === 2 && 
                    <StepProfile 
                        formData={formData}
                        updateFormData={updateFormData}
                    />
                }
                {step === 3 && 
                    <StepAddress 
                        formData={formData}
                        updateFormData={updateFormData}
                    />
                }
                <div className={styles.buttonContainer}>
                    {step > 1 ? (
                        <div className={styles.buttonContent}>
                            <Button
                                text="Anterior"
                                onClick={prevStep}
                                type='button'
                            />
                        </div>
                    ) : (
                        <div />
                    )}

                    <div className={styles.buttonContent}>
                        <Button 
                            type='button'
                            text={step < 3 ? "Próximo" : "Finalizar"} 
                            onClick={nextStep} 
                        />
                    </div>
                </div>
            </div>
        </BoxInput>
    );
}