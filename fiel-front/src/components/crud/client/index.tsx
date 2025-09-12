'use client'
import styles from './client.module.css';
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

    const deleteUser = async(userId: number) => {
        const res = await api.delete("/user", { params: { userId } }) as ApiResponse;

        if(res.message) {
            alert(res.message);
            return;
        }

        const updatedUsers = users.filter(u => u.id !== userId);
        setUsers(updatedUsers);

        alert("Usuario deletado com sucesso");
    };

    const updateActive = async(userId: number, currentActive: boolean) => {
        const res = await api.put("/user/active", { params: { active: currentActive, userId } }) as ApiResponse;

        if(res.message) {
            alert(res.message);
            return;
        }

        const updatedUsers = users.map(u =>
            u.id === userId ? { ...u, active: currentActive } : u
        );
        setUsers(updatedUsers);

        alert(currentActive ? "Usuário ativado com sucesso" : "Usuário inativado com sucesso");
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
                                            onClick={() => user.id !== null && deleteUser(user.id)}
                                        />
                                        <ActionButton
                                            label={user.active ? "Desativar" : "Ativar"}
                                            onClick={() => user.id !== null && updateActive(user.id, !user.active)}
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