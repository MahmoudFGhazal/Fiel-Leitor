'use client'
import { AddressRequest, UserRequest } from '@/api/dtos/requestDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import showToast from '@/utils/showToast';
import { createAccountValidator } from '@/utils/validator/UserValidator';
import { useState } from "react";
import Button from '../buttonComponents/button';
import BoxInput from '../formBox';
import styles from './signIn.module.css';
import StepAddress from "./stepsSign/StepAddress";
import StepProfile from "./stepsSign/StepProfile";
import StepUser from "./stepsSign/StepUser";

interface formData {
    user: UserRequest,
    address: AddressRequest
}

export default function SignInComponent() {
    const [confirmPassword, setConfirmPassword] = useState('');
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState<formData>({
        user: {  
            id: null,
            email: "",
            password: "",
            newPassword: null,
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
            residenceType: null,
            principal: null
        }
    });

    const nextStep = () => {
        const validator = createAccountValidator();
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
        const res: ApiResponse = await api.post("/user/sign", { data: { user: formData.user, address: formData.address} });

        if(res.message) {
            alert(res.message);
            return;
        }

        await showToast('Cadastro concluído com sucesso!');
        window.location.href = "/login";
    };

    const prevStep = () => setStep(step - 1);

    const updateUserData = (data: Partial<UserRequest>) => {
        setFormData(prev => ({
            ...prev,
            user: { ...prev.user, ...data }
        }));
    };

    const updateConfirmPassword = (data: string) => {
        setConfirmPassword(data);
    };

    const updateAddressData = (data: Partial<AddressRequest>) => {
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
                                dataCy="back-button"
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
                            dataCy="next-button"
                        />
                    </div>
                </div>
            </div>
        </BoxInput>
    );
}