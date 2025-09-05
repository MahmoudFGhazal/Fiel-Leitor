'use client'
import styles from './profileConfig.module.css';
import { UserData } from "@/modal/userModal";
import { useEffect, useState } from "react";
import UserFormEdit from "../../userFormEdit";

export default function ProfileConfig() {
    const [user, setUser] = useState<UserData | null>(null);
    const [disable, setDisable] = useState<boolean>(true);
    const [editedUser, setEditedUser] = useState<UserData>({
        id: 0,
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

    useEffect(() => {
        const localUser = localStorage.getItem('currentUser');
        const sessionUser = sessionStorage.getItem('currentUser');

        const currentUser = localUser ? JSON.parse(localUser) 
                                      : sessionUser ? JSON.parse(sessionUser) 
                                      : null;

        if (currentUser) {
            setUser(currentUser);
            setEditedUser(currentUser);
        }
    }, []);
    
    if(!user) {
        return <p>Nenhum usuário logado.</p>;
    }
    
    const handleChange = (field: string, value: string) => {
        setEditedUser(prev => ({ ...prev, [field]: value }));
    };

    const cancelEdit = () => {
        setEditedUser(user);
        setDisable(true);
    };

    const toggleEdit = () => {
        if (!disable) {
            saveUser();
        }
        setDisable(!disable);
    };

    const saveUser = () => {
        setUser(editedUser);

        localStorage.setItem('currentUser', JSON.stringify(editedUser));
        sessionStorage.setItem('currentUser', JSON.stringify(editedUser));

        alert("Dados atualizados com sucesso!");
    };

    return (
        <div>
            <div className={styles.container}>
                <h1>Configurações da Conta</h1>
                {disable ? (
                    <p className={styles.editButton} onClick={toggleEdit}>
                        Editar
                    </p> 
                    ) : (
                        <div className={styles.cancelContainer}>
                            <p className={styles.editButton} onClick={toggleEdit}>
                                Salvar
                            </p> 
                            <p className={styles.editButton} onClick={cancelEdit}>
                                Cancelar
                            </p> 
                        </div>
                    )
                }
            </div>
            <UserFormEdit
                user={editedUser} 
                disable={disable} 
                onChange={handleChange} 
            />
        </div>
    );
}