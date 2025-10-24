'use client'
import { SaleBookResponse } from '@/api/dtos/responseDTOs';
import Button from '@/components/buttonComponents/button';
import Image from 'next/image';
import bookImage from '../../../../public/book.png';
import styles from './orderItem.module.css';

export default function OrderItem({ book }: { book: SaleBookResponse }) {
    return (
        <div className={styles.orderItem}>
            <div className={styles.fieldContent} >
                <Image
                    src={bookImage}
                    alt={book.book?.name ?? ""}
                    className={styles.fieldImage}
                />
            </div>
            <div className={styles.itemInfo}>
                <div className={styles.infoContent}>
                    <p>{book.book?.name}</p>
                    <p>Valor: R${book.price}</p>
                    <p>Quantidade: {book.quantity}</p>
                </div>
                <div className={styles.buttonContainer}>
                    <div className={styles.itemButtons}>
                        <Button 
                            type={'button'}
                        >
                            Adicionar no Carrinho
                        </Button>
                        <Button 
                            type={'button'}
                        >
                            Ver Item
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
}