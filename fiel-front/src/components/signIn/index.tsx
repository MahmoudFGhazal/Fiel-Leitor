'use client'
import styles from './signIn.module.css';
import { useState } from "react";
import StepUser from "./stepsSign/StepUser";
import StepAddress from "./stepsSign/StepAddress";
import StepProfile from "./stepsSign/StepProfile";
import BoxInput from '../formBox';
import Button from '../buttonComponents/button';
import { Address, ApiResponse, User } from '@/api/objects';
import CreateAccountValidator from '@/utils/validator/createAccountValidator';
import showToast from '@/utils/showToast';
import api from '@/api/route';

interface formData {
    user: User,
    address: Address
}

export default function SignInComponent() {
    const [confirmPassword, setConfirmPassword] = useState('');
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState<formData>({
        user: {  
            id: null,
            email: "",
            password: "",
            name: "",
            gender: null,
            birthday: null,
            cpf: "",
            phoneNumber: "",
            active: true
        },
        address: {
            id: null,
            user: null,
            nickname: "",
            number: "",
            complement: "",
            street: "",
            neighborhood: "",
            zip: "",
            city: "",
            state: "",
            country: "",
            streetType: null,
            residenceType: null
        }
    });

    const nextStep = () => {
        const validator = CreateAccountValidator();
        let error = validator.validateStep(step, formData, { confirmPassword });

        if (error) {
            showToast(error);
            return;
        } 

        if (step < 3) {
            setStep(step + 1);
        } else {
            saveUser();
        }
    };

    const saveUser = async () => {
        const res: ApiResponse = await api.post("/user", { data: formData.user });

        if(res.message) {
            alert(res.message);
            return;
        }
        const user = res.data.entity as User;

        setFormData(prev => ({
            ...prev,
            address: {
                ...prev.address,
                user: user
            }
        }));

        await api.post("/address", {
            data: { ...formData.address,
                    user: user }
        });

        await showToast('Cadastro concluído com sucesso!');
        window.location.href = "/login";
    };

    const prevStep = () => setStep(step - 1);

    const updateUserData = (data: Partial<User>) => {
        setFormData(prev => ({
            ...prev,
            user: { ...prev.user, ...data }
        }));
    };

    const updateConfirmPassword = (data: string) => {
        setConfirmPassword(data);
    };

    const updateAddressData = (data: Partial<Address>) => {
        setFormData(prev => ({
            ...prev,
            address: { ...prev.address, ...data }
        }));
    };

    return (
        <BoxInput>
            <div className={styles.formContainer}>
                {step === 1 && 
                    <StepUser 
                        formData={formData.user}
                        confirmPassword={confirmPassword}
                        updateFormData={updateUserData}
                        updateConfirmPassword={updateConfirmPassword}
                    />
                }
                {step === 2 && 
                    <StepProfile 
                        formData={formData.user}
                        updateFormData={updateUserData}
                    />
                }
                {step === 3 && 
                    <StepAddress 
                        formData={formData.address}
                        updateFormData={updateAddressData}
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