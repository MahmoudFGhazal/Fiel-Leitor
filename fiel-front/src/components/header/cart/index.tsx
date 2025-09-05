'use client';
import { FaTrash } from "react-icons/fa";
import { useEffect, useState } from 'react';
import styles from './cart.module.css';
import QuantityButtons from "@/components/quantityButton";
import Button from "@/components/button";
import { CartItem } from "@/modal/productModal";

type Props = {
    onClose: () => void;
};

export default function CartSidebar({ onClose }: Props) {
    const [items, setItems] = useState<CartItem[]>([]);

    useEffect(() => {
        const storedItems = localStorage.getItem('card_items');
        
        if (storedItems) {
            try {
                const parsed = JSON.parse(storedItems);
                if (Array.isArray(parsed)) {
                    setItems(parsed);
                } else {
                    console.warn('Formato inválido em card_items');
                }
            } catch (err) {
                console.error('Erro ao fazer parse de card_items:', err);
            }
        }
    }, []);


    useEffect(() => {
        if(items.length > 0) {
            localStorage.setItem('card_items', JSON.stringify(items));
        }    
    }, [items]);

    const increaseQuantity = (id: number) => {
        setItems(prev =>
            prev.map(item =>
                item.id === id ? { ...item, quantity: item.quantity + 1 } : item
            )
        );
    };

    const decreaseQuantity = (id: number) => {
        setItems(prev =>
            prev.map(item =>
                item.id === id && item.quantity > 1
                    ? { ...item, quantity: item.quantity - 1 }
                    : item
            )
        );
    };

    const removeItem = (id: number) => {
        setItems(prev => prev.filter(item => item.id !== id));
    };

    const handlePurchase = () => {
        const queryString = new URLSearchParams({
            items: JSON.stringify(items)
        }).toString();

        window.location.href = `/sale?${queryString}`;
    };

    return (
        <div className={styles.overlay} onClick={onClose}>
            <div className={styles.cartSidebar}>
                <div className={styles.cartHeader}>
                    <h3>Seu Carrinho</h3>
                    <button onClick={onClose} className={styles.closeButton}>×</button>
                </div>
                <div className={styles.cartItems}>
                    {items.map(item => (
                        <div key={item.id} className={styles.cartItem}>
                            <div className={styles.nameContent}>
                                <span>{item.name}</span>
                                <Button
                                    type='button'
                                    onClick={() => removeItem(item.id)}
                                >
                                    <FaTrash size={15} />
                                </Button>
                            </div>
                            <QuantityButtons
                                quantity={item.quantity}
                                onIncrease={() => increaseQuantity(item.id)}
                                onDecrease={() => decreaseQuantity(item.id)}
                            />
                        </div>
                    ))}
                </div>

                <div className={styles.purchaseButtonContainer}>
                    <Button
                        type='button'
                        text="Comprar"
                        onClick={handlePurchase}
                        disabled={items.length === 0}
                    />
                </div>
            </div>
        </div>
    );
}
