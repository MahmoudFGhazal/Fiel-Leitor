'use client'
import { BookData } from '@/modal/productModal';
import styles from './page.module.css';
import { useEffect, useState } from "react";
import ActionButton from '@/components/buttonComponents/actionButton';
import CreateBookPopup from '@/components/popUpCreate';

export default function CRUDBookComponent() {
    const [books, setBooks] = useState<BookData[]>([]);
    const [createShowPopup, setCreateShowPopup] = useState(false);

    useEffect(() => {
        const updatedBooks = JSON.parse(localStorage.getItem('books') || '[]');
        setBooks(updatedBooks);
    }, []);

    const handleCreate = (newBook: BookData) => {
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
        book.isActive = !book.isActive;  
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
                />
            </div>

            {createShowPopup && (
                <CreateBookPopup 
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
                                    <td>{book.categories}</td>
                                    <td>{book.price}</td>
                                    <td>{book.stock}</td>
                                    <td>
                                        <ActionButton
                                            label="Excluir"
                                            onClick={() => deleteBook(index)}
                                        />
                                        <ActionButton
                                            label="Editar"
                                            onClick={() => deleteBook(index)}
                                        />
                                        <ActionButton
                                            label={book.isActive ? "Desativar" : "Ativar"}
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