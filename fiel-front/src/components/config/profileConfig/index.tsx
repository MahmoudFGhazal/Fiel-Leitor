'use client'
import { ApiResponse, User } from '@/api/objects';
import styles from './profileConfig.module.css';
import { useEffect, useState } from "react";
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import UserFormEdit from '@/components/forms/userFormEdit';

export default function ProfileConfig() {
    const { currentUser } = useGlobal();
    const [disable, setDisable] = useState<boolean>(true);
    const [editedUser, setEditedUser] = useState<User>();
    const [user, setUser] = useState<User>();
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const res = await api.get<ApiResponse>(`/user?id=${currentUser}`);
                if(!res.data) {
                    return;
                }

                const entity = res.data.entity as User;

                if(!entity) {
                    return;
                }

                setUser(entity);
                setEditedUser(entity);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar usuário");
            }  finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, []);
    
    const handleChange = (field: string, value: string) => {
        setEditedUser(prev => {
            if (!prev) return prev;

            if (field === "gender") {
                return {
                    ...prev,
                    gender: { id: Number(value) } as any,
                };
            }

            return {
                ...prev,
                [field as keyof User]: value,
            };
        });
    };

    const cancelEdit = () => {
        setDisable(true);
        setEditedUser(user);
    };

    const toggleEdit = () => {
        if (!disable) saveUser();
        setDisable(!disable);
    };

    const saveUser = async () => {
        try {
            console.log(JSON.stringify(editedUser, null, 2))
            await api.put("/user", { data: editedUser });

            alert("Dados atualizados com sucesso!");
            setDisable(true);
        } catch (err) {
            console.error(err);
            alert("Erro ao salvar usuário");
        }
    };

    if (loading) return <p>Carregando...</p>;

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
            {editedUser && (
                <UserFormEdit
                    user={editedUser} 
                    disable={disable} 
                    onChange={handleChange} 
                /> 
            )} 
        </div>
    );
} 