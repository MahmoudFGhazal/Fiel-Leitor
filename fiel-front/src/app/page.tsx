'use server'
import HomeComponent from '@/components/homeComponent';
import styles from './page.module.css';

export default async function Home() {
    return (
        <div className={styles.container}>
            <HomeComponent />
        </div>
    );
}
