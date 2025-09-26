'use client';
import { ApiResponse } from "@/api/objects";
import api from '@/api/route';
import Button from "@/components/buttonComponents/button";
import InputText from '@/components/inputComponents/inputText';
import { useGlobal } from '@/context/GlobalContext';
import showToast from '@/utils/showToast';
import { changePasswordValidator } from "@/utils/validator/UserValidator";
import { useState } from "react";
import styles from './changePasswordConfig.module.css';

export default function ChangePasswordConfig() {
    const { currentUser } = useGlobal();
    const [currentPassword, setCurrentPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const handleChangePassword = async () => {
        const validator = changePasswordValidator();
        const error = validator.validate(currentPassword, newPassword, confirmPassword);

        if (error) {
            alert(error);
            return;
        }

        try {
            const payload = {
                userId: currentUser,
                currentPassword,
                newPassword
            };

            const res = await api.put<ApiResponse>('/user/password', { params: payload });

            if (res.message) {
                alert(res.message);
                return;
            }

            await showToast("Senha atualizada com sucesso!");

            setCurrentPassword('');
            setNewPassword('');
            setConfirmPassword('');
        } catch (err) {
            console.error(err);
            alert("Erro ao atualizar senha");
        }
    };

    return (
        <div className={styles.formContainer}>
            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputText
                        type="password"
                        text="Senha Atual"
                        value={currentPassword}
                        onChange={(value) => setCurrentPassword(value)}
                        dataCy='current-password-text'
                    />
                </div>
            </div>

            <div className={styles.content}>
                <div className={styles.inputContent}>
                    <InputText
                        type="password"
                        text="Nova Senha"
                        value={newPassword}
                        onChange={(value) => setNewPassword(value)}
                        dataCy='new-password-text'
                    />
                </div>
                <div className={styles.inputContent}>
                    <InputText
                        type="password"
                        text="Confirmar Nova Senha"
                        value={confirmPassword}
                        onChange={(value) => setConfirmPassword(value)}
                        dataCy='confirm-password-text'
                    />
                </div>
            </div>

            <div className={styles.content}>
                <Button
                    type="button"
                    text="Atualizar Senha"
                    onClick={handleChangePassword}
                    dataCy="update-password-button"
                />
            </div>
        </div>
    );
}
