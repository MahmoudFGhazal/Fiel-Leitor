'use client'
import { AddCartRequest } from '@/api/dtos/requestDTOs';
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import Image from 'next/image';
import { useEffect, useState } from 'react';
import bookImage from '../../../public/book.png';
import Button from '../buttonComponents/button';
import QuantityButtons from '../quantityButton';
import styles from './bookComponent.module.css';

interface Props {
    bookId: number | null;
}

export default function BookComponent({ bookId }: Props) {
    const { currentUser } = useGlobal();
    
    const [book, setBook] = useState<BookResponse | null>(null);
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        if (bookId == null) return;
        
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/book', { params: { bookId: bookId } });
                
                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entity = (data.entity ?? data.entity) as BookResponse | null;

                if (entity) {
                    setBook(entity);
                    return;
                }
            } catch (err) {
                console.error("Erro ao carregar livros", err);
            }
        }

        fetchData();
    }, []);

    const handleIncrease = () => {
        setQuantity((prev) => prev + 1);
    };

    const handleDecrease = () => {
        setQuantity((prev) => (prev > 1 ? prev - 1 : 1));
    };

    const handleAddToCart = async () => {
        if (!book || !currentUser) return;

        try {
            const payload: AddCartRequest = {
                userId: currentUser,
                addIds: [
                    {
                        user: null,
                        book: book.id,
                        quantity: quantity
                    }
                ]
            };

            const res = await api.post<ApiResponse>('/cart/add', { data: payload });

            if (res.message) {
                alert(res.message);
                return;
            }

            alert("Livro adicionado ao carrinho!");
        } catch (err) {
            console.error("Erro ao adicionar ao carrinho", err);
            alert("Erro ao adicionar livro ao carrinho.");
        }
    };

    const handlePurchase = () => {
        if (!book) return;

        const saleItems = [
            {
                bookId: book.id ?? 0,
                quantity: quantity,
                fromCart: false 
            }
        ];

        const queryString = new URLSearchParams({
            items: JSON.stringify(saleItems)
        }).toString();

        window.location.href = `/sale?${queryString}`;
    };

    if (!book) {
        return <p>Carregando livro...</p>;
    }

    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <div className={styles.centerInformations}>
                    <Image src={bookImage} alt="Capa do Livro" className={styles.image} />
                    <div>
                        <h1>{book.name}</h1>
                        <strong>Autor</strong>
                    </div>
                </div>
                <div className={styles.saleContainer}>
                    <p>
                        Preço: <strong>R$ {book.price}</strong>
                    </p>
                    {currentUser && (
                        <QuantityButtons
                            quantity={quantity}
                            onIncrease={handleIncrease}
                            onDecrease={handleDecrease}
                        />
                    )}
                    <div className={styles.buttonContent}>
                        <Button 
                            type="button" 
                            text="Comprar" 
                            disabled={!currentUser} 
                            onClick={handlePurchase} 
                        />
                        <Button
                            type="button"
                            text="Carrinho"
                            disabled={!currentUser}
                            onClick={handleAddToCart}
                        />
                    </div>
                </div>
            </div>
            <div className={styles.descrition}>
                <p>description</p>
            </div>
        </div>
    );
}