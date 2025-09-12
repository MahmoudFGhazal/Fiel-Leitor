'use client'
import styles from './page.module.css';
import SelectPaymentMethod from '@/components/selectPaymentMethod';
import CartItemsList from '@/components/cartItemsList';
import { useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useState } from 'react';
import Button from '@/components/buttonComponents/button';
import SelectAddressMethod from '@/components/selectAddressMethod';

export interface CartItem {
    id: number;         
    name: string;        
    price: number;       
    quantity: number;  
    imageUrl?: string;   
}

export default function Sale() {
    const searchParams = useSearchParams();
    const itemsParam = searchParams.get('items');  

    const [items, setItems] = useState<CartItem[]>([]);

    useEffect(() => {
        if (itemsParam) {
            try {
                const parsedItems = JSON.parse(itemsParam);
                setItems(parsedItems);
            } catch (err) {
                console.error('Erro ao parsear os itens:', err);
            }
        }
    }, [itemsParam]);

    const handleIncrease = (id: number) => {
        setItems(prev => prev.map(item =>
            item.id === id ? { ...item, quantity: item.quantity + 1 } : item
        ));
    };

    const handleDecrease = (id: number) => {
        setItems(prev => prev.map(item =>
            item.id === id ? { ...item, quantity: Math.max(item.quantity - 1, 1) } : item
        ));
    };

    const handleRemove = (id: number) => {
        setItems(prev => prev.filter(item => item.id !== id));
    };

    const purchaseTotal = useMemo(() => {
        return items.reduce((acc, item) => acc + (item.price * item.quantity), 0);
    }, [items]);

    return (
        <div className={styles.container}>
            <div className={styles.content}>
                <div>
                    <SelectAddressMethod />
                    <SelectPaymentMethod purchaseTotal={purchaseTotal} />
                </div>
                <CartItemsList
                    items={items}
                    onIncrease={handleIncrease}
                    onDecrease={handleDecrease}
                    onRemove={handleRemove}
                />
            </div>
            <div className={styles.buttonContent}>
                <Button
                    type='button'
                    text='Finalizar'

                />
            </div>
        </div>
    );

}