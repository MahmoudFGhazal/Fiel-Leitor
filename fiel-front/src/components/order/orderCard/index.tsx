'use client'
import { SaleBookResponse, SaleResponse } from '@/api/dtos/responseDTOs';
import { useEffect, useState } from 'react';
import OrderItem from '../orderItem';
import styles from './orderCard.module.css';


export default function OrderCard({ sale }: { sale: SaleResponse }) {
    const [total, setTotal] = useState<number>(0);
    const [books, setBooks] = useState<SaleBookResponse[]>(sale.books ?? []);
    
    useEffect(() => {
        if (!sale?.books?.length) return;

        const sum = sale.books.reduce((acc, book) => acc + (book.price ?? 0), 0);
        setTotal(sum);
    }, [sale]);

    return (
        <div className={styles.orderCard}>
            <div className={styles.orderHeader}>
                <div>
                    <p><strong>Pedido realizado:</strong> 14/10/2025</p>
                    <p><strong>Total:</strong> R$ {total.toFixed(2)}</p>
                </div>
                <div>
                    <p><strong>Pedido nº</strong> {sale.id}</p>
                </div>
            </div>

            <div className={styles.itemsList}>
                {sale.books?.map((book, index) => (
                    <OrderItem key={index} book={book} />
                ))}
            </div>
        </div>
    );
}