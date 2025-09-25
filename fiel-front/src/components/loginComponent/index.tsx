'use client'
import { UserRequest } from '@/api/dtos/requestDTOs';
import { UserResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import FormBox from '@/components/formBox';
import { useGlobal } from '@/context/GlobalContext';
import showToast from '@/utils/showToast';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import InputText from '../inputComponents/inputText';
import styles from './loginComponent.module.css';

interface LoginData {
    email: string,
    password: string,
    refresh: boolean
}

export default function LoginComponent() {
    const router = useRouter();
    const { setCurrentUser } = useGlobal();
    const [formData, setFormData] = useState<LoginData>({
        email: '',
        password: '',
        refresh: false
    });

    const updateFormData = (data: Partial<LoginData>) => {
        setFormData({ ...formData, ...data });
    };

    const executeLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const loginData: UserRequest = {
            email: formData.email,
            password: formData.password,
            active: null,
            birthday: null,
            cpf: null,
            gender: null,
            id: null,
            name: null,
            phoneNumber: null
        }

        const res = await api.post<ApiResponse>('/user/login', { data: loginData });

        if(res.message) {
            alert(res.message);
            return;
        }

        const data = res.data;

        if (!data) {
            alert('Email ou senha incorretos.');
            return;
        }
        
        const user = (data.entity ?? data) as UserResponse | null;

        if (!user || !user.id) {
            alert('Email ou senha incorretos.');
            return;
        }

        if (!user.active) {
            await showToast('Usuario Inativado!');
            return;
        }

        await showToast('Login efetuado com sucesso!');

        if (formData.refresh) {
            localStorage.setItem('currentUser', JSON.stringify(user.id));
        } 
        setCurrentUser(user.id);

        router.push("/");

        await showToast("oii")
    }
    
    return (
        <div className={styles.loginCotent}>
            <FormBox>
                <form onSubmit={executeLogin} className={styles.formContent}>
                    <div className={styles.inputContainer}>
                        <div className={styles.inputContent}>
                            <InputText
                                type="email"
                                text="Email"
                                value={formData.email}
                                onChange={(val: string) => updateFormData({ email: val })}
                                dataCy='email-text'
                            />
                            <InputText
                                type="password"
                                text="Senha"
                                value={formData.password}
                                onChange={(val: string) => updateFormData({ password: val })} 
                                dataCy='password-text'                         
                            />
                        </div>
                        {/* <InputCheckBox 
                            text='Manter-se Contectado'
                            checked={formData.refresh}
                            onChange={(val: boolean) => updateFormData({ refresh: val })} 
                            dataCy=''                         
                        /> */}
                    </div>
                    <div className={styles.buttonContent}>
                        <Button type='submit' text="Entrar" dataCy='submit-button' />
                        <a className={styles.signButton} href='/sign'>
                            <Button type='button' text='Cadastre-se' />
                        </a>
                    </div>
                </form>
            </FormBox>
        </div>
    );
}