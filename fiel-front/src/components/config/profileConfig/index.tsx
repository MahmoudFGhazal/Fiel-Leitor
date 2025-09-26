'use client'
import { ApiResponse } from '@/api/objects';
import styles from './profileConfig.module.css';
import { useEffect, useState } from "react";
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import UserFormEdit from '@/components/forms/userFormEdit';
import { UserRequest } from '@/api/dtos/requestDTOs';
import { UserResponse } from '@/api/dtos/responseDTOs';
import { toRequestUser } from '@/utils/toRequest';
import ActionButton from '@/components/buttonComponents/actionButton';

export default function ProfileConfig() {
    const { currentUser } = useGlobal();
    const [disable, setDisable] = useState<boolean>(true);
    const [editedUser, setEditedUser] = useState<UserRequest>();
    const [user, setUser] = useState<UserResponse>();
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const res = await api.get<ApiResponse>(`/user?id=${currentUser}`);
                if(!res.data) {
                    return;
                }

                const entity = res.data.entity as UserResponse;

                if(!entity) {
                    return;
                }

                setUser(entity);

                const userReq: UserRequest = toRequestUser(entity);

                setEditedUser(userReq);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar usuário");
            }  finally {
                setLoading(false);
            }
        };

        if(!currentUser) return;

        fetchUser();
    }, [currentUser]);
    
    const handleChange = (field: string, value: string) => {
        setEditedUser(prev => {
            if (!prev) return prev;

            if (field === "gender") {
                return {
                    ...prev,
                    gender: Number(value),
                };
            }

            return {
                ...prev,
                [field as keyof UserRequest]: value,
            };
        });
    };

    const cancelEdit = () => {
        setDisable(true);

        const userReq = toRequestUser(user ?? null);
        setEditedUser(userReq);
    };

    const toggleEdit = () => {
        if (!disable) saveUser();
        setDisable(!disable);
    };

    const saveUser = async () => {
        try {
            const res = await api.put("/user", { data: editedUser }) as ApiResponse;

            if (res.message) {
                alert(res.message);
                return;
            }

            const entity = res.data.entity as UserResponse;

            if(!entity) {
                alert("Erro ao atualizar usuario");
                return;
            }

            setUser(entity);
            alert("Dados atualizados com sucesso!");
            setDisable(true);
        } catch (err) {
            console.error(err);
            alert("Erro ao salvar usuário");
            setEditedUser(toRequestUser(user ?? null));
        }
    };

    if (loading) return <p>Carregando...</p>;

    return (
        <div>
            <div className={styles.container}>
                <h1>Configurações da Conta</h1>
                {disable ? (
                        <ActionButton 
                            label="Editar"
                            color='blue'
                            onClick={toggleEdit}
                        />
                    ) : (
                        <div className={styles.cancelContainer}>
                            <ActionButton 
                                label='Salvar'
                                color='green'
                                onClick={toggleEdit}
                            />
                            <ActionButton 
                                label='Cancelar'
                                color='red'
                                onClick={cancelEdit}
                            />
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