'use client'
import OrderItem from '../orderItem';
import styles from './orderCard.module.css';

type Item = {
  name: string;
  quantity: number;
  image: string;
};

type OrderCardProps = {
  orderNumber: string;
  date: string;
  total: string;
  items: Item[];
};

export default function OrderCard({ orderNumber, date, total, items }: OrderCardProps) {
    return (
        <div className={styles.orderCard}>
            <div className={styles.orderHeader}>
                <div>
                    <p><strong>Pedido realizado:</strong> {date}</p>
                    <p><strong>Total:</strong> {total}</p>
                </div>
                <div>
                    <p><strong>Pedido nº</strong> {orderNumber}</p>
                    <button>Fatura</button>
                </div>
            </div>

            <div className={styles.itemsList}>
                {items.map((item, index) => (
                    <OrderItem key={index} {...item} />
                ))}
            </div>
        </div>
    );
}