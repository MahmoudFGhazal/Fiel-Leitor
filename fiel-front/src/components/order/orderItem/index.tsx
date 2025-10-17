'use client'
import { SaleBookResponse } from '@/api/dtos/responseDTOs';
import Image from 'next/image';
import bookImage from '../../../../public/book.png';
import styles from './orderItem.module.css';

export default function OrderItem({ book }: { book: SaleBookResponse }) {
    return (
        <div className={styles.orderItem}>
            <Image
                src={bookImage}
                alt={book.book?.name ?? ""}
                className={styles.fieldImage}
            />
            <div className={styles.itemInfo}>
                <p>{book.book?.name}</p>
                <p>Quantidade: {book.quantity}</p>
                <div className={styles.itemButtons}>
                    <button>Comprar novamente</button>
                    <button>Ver o seu item</button>
                </div>
            </div>
        </div>
    );
}