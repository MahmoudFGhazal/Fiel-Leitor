'use client';
import { useMemo, useState } from 'react';
import styles from './cartItemsList.module.css';

type CartItem = {
  id: number;
  name: string;
  price: number;
  quantity: number;
};

interface CartItemsListProps {
    items: CartItem[];
}

export default function CartItemsList({ items }: CartItemsListProps) {
    const [couponCode, setCouponCode] = useState('');

    const totalPrice = useMemo(
        () => items.reduce((total, item) => total + (item.price ?? 0) * item.quantity, 0),
        [items]
    );

    if (items.length === 0) {
        return <p>Seu carrinho está vazio.</p>;
    }

    return (
        <div className={styles.cartItemsContainer}>
            <h3>Itens</h3>
            <div className={styles.cartItemsList}>
                {items.map(item => (
                    <div key={item.id} className={styles.cartItem}>
                        <div className={styles.itemDetails}>
                            <span>{item.name}</span>
                            <strong>R$ {(item.price ?? 0).toFixed(2)}</strong>
                        </div>
                        <p>{item.quantity}</p>
                    </div>
                ))}
            </div>

            <div className={styles.total}>
                <div className={styles.couponSection}>
                    <h4>Usar Cupom</h4>
                    <input
                        type="text"
                        placeholder="Digite o código do cupom"
                        value={couponCode}
                        onChange={(e) => setCouponCode(e.target.value)}
                        className={styles.couponInput}
                    />
                </div>
                <h3>Total: R$ {totalPrice.toFixed(2)}</h3>
            </div>
        </div>
    );
}
