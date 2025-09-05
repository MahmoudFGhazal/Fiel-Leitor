'use client';

import { useEffect, useState } from 'react';
import styles from './page.module.css';
import Image from 'next/image';
import bookImage from '@/../public/book.png';
import { BookData, CartItem } from '@/modal/productModal';
import Button from '@/components/button';
import QuantityButtons from '@/components/quantityButton';
import { useSearchParams } from 'next/navigation';

export default function BookPage() {
    const searchParams = useSearchParams();
    const bookIdParam = searchParams.get('bookid');
    const bookId = bookIdParam ? Number(bookIdParam) : null;
    
    const [book, setBook] = useState<BookData | null>(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        const currentUser =
            JSON.parse(localStorage.getItem('currentUser') || 'null') ||
            JSON.parse(sessionStorage.getItem('currentUser') || 'null');
        setIsLoggedIn(!!currentUser);
    }, []);

    useEffect(() => {
        if (typeof window === 'undefined' || bookId == null) return;

        const storedBooks = localStorage.getItem('books');
        if (storedBooks) {
            try {
                const books: BookData[] = JSON.parse(storedBooks);
                const foundBook = books.find((b) => b.id === bookId);

                if (foundBook) {
                    setBook(foundBook);
                } else {
                    setBook(null);
                }
            } catch (err) {
                console.error('Erro ao ler livros do localStorage:', err);
            }
        } else {
            console.error("Livros não encontrados no localStorage");
        }
    }, [bookId]);

    const handleIncrease = () => {
        setQuantity((prev) => prev + 1);
    };

    const handleDecrease = () => {
        setQuantity((prev) => (prev > 1 ? prev - 1 : 1));
    };

    const handleAddToCart = () => {
        if (!book) return;

        const cartKey = 'card_items';
        const storedItems = localStorage.getItem(cartKey);
        const items: CartItem[] = storedItems ? JSON.parse(storedItems) : [];

        const existingItemIndex = items.findIndex((item) => item.id === book.id);
        if (existingItemIndex !== -1) {
        items[existingItemIndex].quantity += quantity;
        } else {
            items.push({
                id: book.id,
                name: book.name,
                quantity: quantity,
                price: book.price
            });
        }

        localStorage.setItem(cartKey, JSON.stringify(items));
        alert(`${book.name} adicionado ao carrinho!`);
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
                        <strong>{book.author}</strong>
                    </div>
                </div>
                <div className={styles.saleContainer}>
                    <p>
                        Preço: <strong>R$ {book.price.toFixed(2)}</strong>
                    </p>
                    {isLoggedIn && (
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
                            disabled={!isLoggedIn} 
                            onClick={handlePurchase} 
                        />
                        <Button
                            type="button"
                            text="Carrinho"
                            disabled={!isLoggedIn}
                            onClick={handleAddToCart}
                        />
                    </div>
                </div>
            </div>
            <div className={styles.descrition}>
                <p>{book.description}</p>
            </div>
        </div>
    );
}
