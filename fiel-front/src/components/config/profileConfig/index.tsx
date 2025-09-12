'use client'
import { ApiResponse, User } from '@/api/objects';
import styles from './profileConfig.module.css';
import { useEffect, useState } from "react";
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';

export default function ProfileConfig() {
    const { currentUser, setCurrentUser } = useGlobal();
    const [disable, setDisable] = useState<boolean>(true);
    const [editedUser, setEditedUser] = useState<User>({
        id: 0,
        email: "",
        password: "",
        name: "",
        gender: null,
        birthday: null,
        cpf: "",
        phoneNumber: "",
        active: true
    });

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

                setEditedUser(entity);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar usuário");
            }
        };

        fetchUser();
    }, []);
    
    const handleChange = (field: keyof User, value: string) => {
        setEditedUser(prev => ({ ...prev, [field]: value }));
    };

    const cancelEdit = () => {
        setDisable(true);

        if (currentUser) {
            const storedUser = JSON.parse(localStorage.getItem('currentUser') || 'null') as User;
            if (storedUser?.id === currentUser) {
                setEditedUser(storedUser);
            }
        }
    };

    const toggleEdit = () => {
        if (!disable) saveUser();
        setDisable(!disable);
    };

    const saveUser = async () => {
        try {
            await api.post("/user/update", { data: editedUser });

            //setCurrentUser(editedUser.id);
            localStorage.setItem('currentUser', JSON.stringify(editedUser));

            alert("Dados atualizados com sucesso!");
            setDisable(true);
        } catch (err) {
            console.error(err);
            alert("Erro ao salvar usuário");
        }
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
            {/* <UserFormEdit
                user={editedUser} 
                disable={disable} 
                onChange={handleChange} 
            /> */}
        </div>
    );
} 