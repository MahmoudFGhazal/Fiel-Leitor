'use client'
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import Image from 'next/image';
import { useSearchParams } from 'next/navigation';
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
    const searchParams = useSearchParams();
    const bookIdParam = searchParams.get('bookId');
    
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
                console.error("Erro ao carregar cartões", err);
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

    const handleAddToCart = () => {
        
    };

    const handlePurchase = () => {
        if (!book) return;

        const cartItems = [
            {
                id: book.id,
                name: book.name,
                quantity: quantity,
                price: book.price
            }
        ];

        const queryString = new URLSearchParams({
            items: JSON.stringify(cartItems)
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