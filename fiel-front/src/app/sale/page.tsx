'use client'
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import CartItemsList from '@/components/cartItemsList';
import SelectAddressMethod from '@/components/selectAddressMethod';
import SelectPaymentMethod from '@/components/selectPaymentMethod';
import { useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import styles from './page.module.css';

export interface SaleItemParam {
    bookId: number;
    quantity: number;
    fromCart: boolean;
}

export interface SaleItemDetail {
    book: BookResponse;
    quantity: number;
    fromCart: boolean;
}

export default function Sale() {
    const searchParams = useSearchParams();
    const itemsParam = searchParams.get('items');
    const [items, setItems] = useState<SaleItemDetail[]>([]);
    const [total, setTotal] = useState(0);

    useEffect(() => {
        async function fetchBooks(parsedItems: SaleItemParam[]) {
            try {
                const bookIds = parsedItems.map(i => i.bookId);
                const res = await api.get<ApiResponse>('/book/list', { params: { ids: bookIds } });

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data) as BookResponse[];

                const detailed = parsedItems.map(p => ({
                    book: entities.find(e => e.id === p.bookId)!,
                    quantity: p.quantity,
                    fromCart: p.fromCart
                }));

                setItems(detailed);
            } catch (err) {
                console.error("Erro ao buscar detalhes dos livros:", err);
            }
        }

        if (itemsParam) {
            try {
                const parsed: SaleItemParam[] = JSON.parse(itemsParam);
                fetchBooks(parsed);
            } catch (err) {
                console.error("Erro ao parsear itemsParam:", err);
            }
        }
    }, [itemsParam]);

    useEffect(() => {
        const totalValue = items.reduce((acc, item) => acc + (Number(item.book.price) * item.quantity), 0);
        setTotal(totalValue);
    }, [items]);

    const handleIncrease = (bookId: number) => {
        setItems(prev => prev.map(item =>
            item.book.id === bookId ? { ...item, quantity: item.quantity + 1 } : item
        ));
    };

    const handleDecrease = (bookId: number) => {
        setItems(prev => prev.map(item =>
            item.book.id === bookId ? { ...item, quantity: Math.max(item.quantity - 1, 1) } : item
        ));
    };

    const handleRemove = (bookId: number) => {
        setItems(prev => prev.filter(item => item.book.id !== bookId));
    };

    return (
        <div className={styles.container}>
            <div className={styles.content}>
                <div>
                    <SelectAddressMethod />
                    <SelectPaymentMethod purchaseTotal={total} />
                </div>

                <CartItemsList
                    items={items.map(i => ({
                        id: i.book.id ?? 0,
                        name: i.book.name ?? '',
                        price: i.book.price ?? 0,
                        quantity: i.quantity
                    }))}
                    onIncrease={handleIncrease}
                    onDecrease={handleDecrease}
                    onRemove={handleRemove}
                />
            </div>

            <div className={styles.buttonContent}>
                <Button
                    type='button'
                    text='Finalizar Compra'

                />
            </div>
        </div>
    );

}