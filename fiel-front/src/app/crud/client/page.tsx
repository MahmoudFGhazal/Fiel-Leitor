'use client'
import styles from './page.module.css';
import { useEffect, useState } from "react";
import ActionButton from '@/components/actionButton';
import { ApiResponse, User } from '@/api/objects';
import api from '@/api/route';

export default function CRUDClientComponent() {
    const [users, setUsers] = useState<User[]>([]);
    
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const res = await api.get("/user/all") as ApiResponse;

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const users = res.data.entities as User[];
                setUsers(users);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar usuários");
            }
        };

        fetchUsers();
    }, []);

    const deleteUser = async(index: number) => {
        const updatedUsers = [...users];
        updatedUsers.splice(index, 1);
        setUsers(updatedUsers);
        localStorage.setItem('users', JSON.stringify(updatedUsers));    
        alert('Exclusão concluído com sucesso!');
    };

    const updateActive = async(index: number) => {
        const updatedUsers = [...users];
        const user = updatedUsers[index];
        user.active = !user.active;  
        setUsers(updatedUsers);
        localStorage.setItem('users', JSON.stringify(updatedUsers));  
    }

    return (
        <div className={styles.container}>
            <h2>Usuários cadastrados</h2>
            <div className={styles.tableContent}>
                {users.length === 0 ? (
                    <p>Nenhum usuário cadastrado.</p>
                ) : (
                    <table className={styles.table}>
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>CPF</th>
                                <th>Telefone</th>
                                <th>Email</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {users.map((user, index) => (
                                <tr key={index}>
                                    <td>{user.name}</td>
                                    <td>{user.cpf}</td>
                                    <td>{user.phoneNumber}</td>
                                    <td>{user.email}</td>
                                    <td>
                                        <ActionButton
                                            label="Excluir"
                                            onClick={() => deleteUser(index)}
                                        />
                                        <ActionButton
                                            label={user.active ? "Desativar" : "Ativar"}
                                            onClick={() => updateActive(index)}
                                        />
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}