'use client'
import { UserData } from '@/modal/userModal';
import styles from './page.module.css';
import { useEffect, useState } from "react";
import ActionButton from '@/components/actionButton';

export default function CRUDClientComponent() {
    const [users, setUsers] = useState<UserData[]>([]);
    
    useEffect(() => {
        const storedUsers = JSON.parse(localStorage.getItem('users') || '[]');
        setUsers(storedUsers);
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
        user.isActive = !user.isActive;  
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
                                            label={user.isActive ? "Desativar" : "Ativar"}
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