'use server'
import SaleComponent from '@/components/saleComponent';
import styles from './page.module.css';

export default async function Sale() {
    return (
        <div className={styles.container}>
            <SaleComponent />
        </div>
    );

}