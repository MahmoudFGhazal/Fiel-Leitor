'use client'
import { BookRequest } from '@/api/dtos/requestDTOs';
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import ActionButton from '@/components/buttonComponents/actionButton';
import PopUpBookCreate from '@/components/forms/popUpBook';
import { useEffect, useState } from "react";
import styles from './book.module.css';

export default function BookCrudComponent() {
    const [books, setBooks] = useState<BookResponse[]>([]);
    const [createShowPopup, setCreateShowPopup] = useState(false);
    const [editShowPopup, setEditShowPopup] = useState(false);
    const [selectedBook, setSelectedBook] = useState<BookResponse | undefined>(undefined);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/book/all');
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
                console.error("Erro ao carregar livros", err);
            }
        }

        fetchData();
    }, []);

    const handleCreate = async (newBook: BookRequest) => {
        try {
            const res = await api.post<ApiResponse>('/book', { data: newBook });
            if (res.message) {
                alert(res.message);
                return;
            }
            const data = res.data;
            const entity = (data.entity ?? data) as BookResponse | null;

            if (!entity) {
                return;
            }
        
            setBooks(prev => [...prev, entity]);

            alert('Livro criado com sucesso!');
            setCreateShowPopup(false);
        } catch (err) {
            console.error("Erro ao carregar livros", err);
        }
    }; 

    const handleEdit = async (editBook: BookRequest) => {
        try {
            const res = await api.put<ApiResponse>('/book', { data: editBook });
            if (res.message) {
                alert(res.message);
                return;
            }
            const data = res.data;
            const entity = (data.entity ?? data) as BookResponse | null;

            if (!entity) {
                return;
            }

            setBooks(prev =>
                prev.map(b => (b.id === entity.id ? { ...b, ...entity } : b))
            );

            alert('Livro atualizado com sucesso!');
            setEditShowPopup(false);
        } catch (err) {
            console.error("Erro ao carregar livros", err);
        }
    }; 

    const deleteBook = async(bookId: number) => {
        try {
            const res = await api.put<ApiResponse>('/book/delete', { params: { bookId: bookId}});
            if (res.message) {
                alert(res.message);
                return;
            }
            const data = res.data;
            const entity = (data.entity ?? data) as BookResponse | null;

            if (!entity) {
                return;
            }

            setBooks(prev => prev.filter(b => b.id !== bookId));

            alert('Exclusão concluído com sucesso!');
        } catch (err) {
            console.error("Erro ao carregar livros", err);
        }
    };

    const updateActive = async(bookId: number) => {
        const idx = books.findIndex(b => b.id === bookId);
        if (idx === -1) return;

        const current = books[idx];
        const nextActive = !current.active;

        try {
            const res = await api.put<ApiResponse>('/book/active', { params: { bookId: bookId, active: nextActive }});
            if (res.message) {
                alert(res.message);
                return;
            }
            const data = res.data;
            const entity = (data.entity ?? data) as BookResponse | null;

            if (!entity) {
                return;
            }

            setBooks(prev =>
                prev.map(b => (b.id === bookId ? { ...b, active: nextActive } : b))
            );
            
            alert(nextActive ? 'Livro ativado!' : 'Livro desativado!');
        } catch (err) {
            console.error("Erro ao carregar livros", err);
        }
    }

    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <h2>Livros cadastrados</h2>
                <div className={styles.createContent} >
                    <ActionButton 
                        label='Criar' 
                        onClick={() => setCreateShowPopup(true)}
                        color='green'
                        dataCy='create-button'
                    />
                </div>
            </div>

            {createShowPopup && (
                <PopUpBookCreate 
                    onClose={() => setCreateShowPopup(false)}
                    onSave={handleCreate}
                />
            )}

            {editShowPopup && (
                <PopUpBookCreate 
                    onClose={() => { setEditShowPopup(false); setSelectedBook(undefined); }}
                    onSave={handleEdit}        
                    book={selectedBook} 
                />
            )}

            <div className={styles.tableContent}>
                {books.length === 0 ? (
                    <p>Nenhum livro cadastrado.</p>
                ) : (
                    <table className={styles.table}>
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Nome</th>
                                <th>Categoria</th>
                                <th>Preço</th>
                                <th>Estoque</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {books.map((book) => (
                                <tr key={book.id}>
                                    <td>{book.id}</td>
                                    <td>{book.name}</td>
                                    <td>{book.category?.category}</td>
                                    <td>R${book.price}</td>
                                    <td>{book.stock}</td>
                                    <td>
                                        <ActionButton
                                            label="Excluir"
                                            onClick={() => deleteBook(book.id!)}
                                            color='orange'
                                            dataCy='delete-button'
                                        />
                                        <ActionButton
                                            label="Editar"
                                            onClick={() => {
                                                setEditShowPopup(true)
                                                setSelectedBook(book)
                                            }}
                                            color='blue'
                                            dataCy='edit-button'
                                        />
                                        <ActionButton
                                            label={book.active ? "Desativar" : "Ativar"}
                                            onClick={() => updateActive(book.id!)}
                                            color={book.active ? 'red' : 'green'}
                                            dataCy='active-button'
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