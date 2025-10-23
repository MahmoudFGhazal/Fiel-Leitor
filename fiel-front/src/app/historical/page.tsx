'use server'
import ListSales from "@/components/listSales";
import styles from './page.module.css';

export default async function Historical() {
    return (
        <div className={styles.container}>
            <h1>Seus pedidos</h1>
            <ListSales />
        </div>
    );
}