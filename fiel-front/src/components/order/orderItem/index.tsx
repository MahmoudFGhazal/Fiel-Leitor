'use client'
import styles from './orderItem.module.css';

type OrderItemProps = {
  name: string;
  quantity: number;
  image: string;
};

export default function OrderItem({ name, quantity, image }: OrderItemProps) {
    return (
        <div className={styles.orderItem}>
            <img src={image} alt={name} className={styles.itemImage} />
            <div className={styles.itemInfo}>
                <p>{name}</p>
                <p>Quantidade: {quantity}</p>
                <div className={styles.itemButtons}>
                    <button>Comprar novamente</button>
                    <button>Ver o seu item</button>
                </div>
            </div>
        </div>
    );
}