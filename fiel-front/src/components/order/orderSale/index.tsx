'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { useEffect, useMemo, useState } from 'react';
import OrderItem from '../orderItem';
import styles from './orderCard.module.css';

function formatDate(d: Date | string | null | undefined) {
    if (!d) return '';
    const dt = d instanceof Date ? d : new Date(d);
    return isNaN(dt.getTime()) ? '' : dt.toLocaleDateString('pt-BR');
}

export default function OrderSale({ sale }: { sale: SaleResponse }) {
    const [total, setTotal] = useState<number>(0);
    console.log(JSON.stringify(sale, null, 2))
    useEffect(() => {
        if (!sale?.saleBooks?.length) return;
        console.log(JSON.stringify(sale, null, 2))
        const sum = sale.saleBooks.reduce((acc, book) => acc + (book.price ?? 0), 0);
        setTotal(sum);
    }, [sale]);

    const createdAtStr = useMemo(() => formatDate(sale?.createdAt), [sale?.createdAt]);

    return (
        <div className={styles.orderSale}>
            <div className={styles.orderHeader}>
                <div>
                    <p><strong>Pedido realizado:</strong> {createdAtStr}</p>
                    <p><strong>Total:</strong> R$ {total.toFixed(2)}</p>
                </div>
                <div>
                    <p><strong>Pedido nº</strong> {sale.id}</p>
                </div>
            </div>

            <div className={styles.itemsList}>
                {sale.saleBooks?.map((book, index) => (
                    <OrderItem key={index} book={book} />
                ))}
            </div>
        </div>
    );
}