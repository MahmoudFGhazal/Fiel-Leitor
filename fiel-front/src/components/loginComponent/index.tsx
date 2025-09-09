'use client'
import { useState } from 'react';
import styles from './loginComponent.module.css';

import Button from '@/components/button';
import Input from '@/components/input';
import { LoginData, UserData } from '@/modal/userModal';
import FormBox from '@/components/formBox';

export default function LoginComponent() {
    const [formData, setFormData] = useState<LoginData>({
        email: '',
        password: '',
        refresh: false
    });

    const updateFormData = (data: Partial<LoginData>) => {
        setFormData({ ...formData, ...data });
    };

    const showToast = (msg: string) => {
        return new Promise<void>((resolve) => {
            alert(msg); 
            resolve();
        });
    };

    const executeLogin = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const users = JSON.parse(localStorage.getItem('users') || '[]');
        const matchedUser = users.find(
            (user: UserData) =>
                user.email === formData.email &&
                user.password === formData.password
        );

        if(matchedUser) {
            if(!matchedUser.isActive) {
                await showToast('Usuario Inativado!');
                return;
            }

            await showToast('Login efetuado com sucesso!');
            window.location.href = "/";

            if (formData.refresh) {
                localStorage.setItem('currentUser', JSON.stringify(matchedUser));
            } else {
                sessionStorage.setItem('currentUser', JSON.stringify(matchedUser));
            }
        }else {
            alert('Email ou senha incorretos.');
        }
    }
    
    return (
        <div className={styles.container}>
            <FormBox>
                <form onSubmit={executeLogin} className={styles.formContent}>
                    <div className={styles.form}>
                        <div className={styles.inputContent}>
                            <Input
                                type="email"
                                text="Email"
                                value={formData.email}
                                onChange={(val: string | boolean | string[]) => {
                                    if (typeof val === "string") {
                                        updateFormData({ email: val });
                                    }
                                }}
                            />
                            <Input
                                type="password"
                                text="Senha"
                                value={formData.password}
                                onChange={(val: string | boolean | string[]) => {
                                    if (typeof val === "string") {
                                        updateFormData({ password: val });
                                    }
                                }}                            
                            />
                        </div>
                        <label className={styles.checkbox}>
                            <div>
                                <Input 
                                    type='checkbox'
                                    checked={formData.refresh}
                                    onChange={(val: string | boolean | string[]) => {
                                        if (typeof val === "boolean") {
                                            updateFormData({ refresh: val });
                                        }
                                    }}                            
                                />
                            </div>
                            Manter-se conectado
                        </label>
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