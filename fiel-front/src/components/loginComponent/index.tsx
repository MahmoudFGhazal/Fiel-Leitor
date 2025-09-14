'use client'
import { useState } from 'react';
import styles from './loginComponent.module.css';
const { setCurrentUser } = useGlobal();
import Button from '@/components/buttonComponents/button';
import FormBox from '@/components/formBox';
import { ApiResponse, User } from '@/api/objects';
import InputText from '../inputComponents/inputText';
import InputCheckBox from '../inputComponents/inputCheckBox';
import api from '@/api/route';
import showToast from '@/utils/showToast';
import { useGlobal } from '@/context/GlobalContext';

interface LoginData {
    email: string,
    password: string,
    refresh: boolean
}

export default function LoginComponent() {
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

        const loginData: User = {
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

        const user = (data.entity ?? data) as User | null;

        if (!user || !user.id) {
            alert('Email ou senha incorretos.');
            return;
        }

        if (!user.active) {
            await showToast('Usuario Inativado!');
            return;
        }

        await showToast('Login efetuado com sucesso!');
        window.location.href = "/";

        if (formData.refresh) {
            localStorage.setItem('currentUser', JSON.stringify(user.id));
            setCurrentUser(user.id);
        } else {
            setCurrentUser(user.id);
        }
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
                            />
                            <InputText
                                type="password"
                                text="Senha"
                                value={formData.password}
                                onChange={(val: string) => updateFormData({ password: val })}                          
                            />
                        </div>
                        <InputCheckBox 
                            text='Manter-se Contectado'
                            checked={formData.refresh}
                            onChange={(val: boolean) => updateFormData({ refresh: val })}                          
                        />
                    </div>
                    <div className={styles.buttonContent}>
                        <Button type='submit' text="Entrar" />
                        <a className={styles.signButton} href='/sign'>
                            <Button type='button' text='Cadastre-se' />
                        </a>
                    </div>
                </form>
            </FormBox>
        </div>
    );
}