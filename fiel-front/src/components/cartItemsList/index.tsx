'use client';
import { FaTrash } from 'react-icons/fa';
import styles from './cartItemsList.module.css';
import QuantityButtons from '@/components/quantityButton';
import Button from '@/components/button';
import { useMemo, useState } from 'react';

type CartItem = {
  id: number;
  name: string;
  price: number;
  quantity: number;
};

interface CartItemsListProps {
    items: CartItem[];
    onIncrease: (id: number) => void;
    onDecrease: (id: number) => void;
    onRemove: (id: number) => void;
}

export default function CartItemsList({ items, onIncrease, onDecrease, onRemove }: CartItemsListProps) {
    const [couponCode, setCouponCode] = useState('');
    const [couponValue, setCouponValue] = useState(0);

    const totalPrice = useMemo(
        () => items.reduce((total, item) => total + (item.price ?? 0) * item.quantity, 0),
        [items]
    );

    const finalPrice = Math.max(totalPrice - couponValue, 0); // evita negativo

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
                        <QuantityButtons
                            quantity={item.quantity}
                            onIncrease={() => onIncrease(item.id)}
                            onDecrease={() => onDecrease(item.id)}
                        />
                        <Button
                            type='button'
                            onClick={() => onRemove(item.id)}
                        >
                            <FaTrash size={15} />
                        </Button>
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
