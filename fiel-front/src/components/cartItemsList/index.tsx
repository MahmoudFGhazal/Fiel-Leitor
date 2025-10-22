'use client';
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

    if (items.length === 0) {
        return <p>Seu carrinho está vazio.</p>;
    }

    return (
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
    );
}
