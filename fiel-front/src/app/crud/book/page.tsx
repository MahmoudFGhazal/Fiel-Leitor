'use client'
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import ActionButton from '@/components/buttonComponents/actionButton';
import PopUpBookCreate from '@/components/forms/popUpBook';
import { useEffect, useState } from "react";
import styles from './page.module.css';

export default function CRUDBookComponent() {
    const [books, setBooks] = useState<BookResponse[]>([]);
    const [createShowPopup, setCreateShowPopup] = useState(false);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/book');
                if (res.message) {
                    alert(res.message);
                    return;
                }
                const data = res.data;
                const entities = (data.entities ?? data) as BookResponse[] | null;

                if (!entities?.length) {
                    setBooks([]);
                    return;
                }
                setBooks(entities);
            } catch (err) {
                console.error("Erro ao carregar vendas", err);
            }
        }

        fetchData();
    }, []);

    const handleCreate = (newBook: BookResponse) => {
        const updatedBooks = [...books, newBook];
        setBooks(updatedBooks);
        localStorage.setItem('books', JSON.stringify(updatedBooks));
    }; 

    const deleteBook = async(index: number) => {
        const updatedBooks = [...books];
        updatedBooks.splice(index, 1);
        setBooks(updatedBooks);
        localStorage.setItem('books', JSON.stringify(updatedBooks));    
        alert('Exclusão concluído com sucesso!');
    };

    const updateActive = async(index: number) => {
        const updatedBooks = [...books];
        const book = updatedBooks[index];
        book.active = !book.active;  
        setBooks(updatedBooks);
        localStorage.setItem('books', JSON.stringify(updatedBooks));  
    }

    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <h2>Livros cadastrados</h2>
                <ActionButton 
                    label='Criar' 
                    onClick={() => setCreateShowPopup(true)}
                    color='green'
                />
            </div>

            {createShowPopup && (
                <PopUpBookCreate 
                    onClose={() => setCreateShowPopup(false)}
                    onCreate={handleCreate}
                />
            )}

            <div className={styles.tableContent}>
                {books.length === 0 ? (
                    <p>Nenhum usuário cadastrado.</p>
                ) : (
                    <table className={styles.table}>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Categoria</th>
                                <th>Preço</th>
                                <th>Estoque</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {books.map((book, index) => (
                                <tr key={index}>
                                    <td>{book.id}</td>
                                    <td>{book.name}</td>
                                    <td>{book.category?.category}</td>
                                    <td>R${book.price}</td>
                                    <td>{book.stock}</td>
                                    <td>
                                        <ActionButton
                                            label="Excluir"
                                            onClick={() => deleteBook(index)}
                                            color='orange'
                                        />
                                        <ActionButton
                                            label="Editar"
                                            onClick={() => deleteBook(index)}
                                            color='blue'
                                        />
                                        <ActionButton
                                            label={book.active ? "Desativar" : "Ativar"}
                                            onClick={() => updateActive(index)}
                                            color={book.active ? 'red' : 'green'}
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