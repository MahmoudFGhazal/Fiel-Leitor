'use server'
import LoginComponent from '@/components/loginComponent';
import styles from './page.module.css';

export default async function Login() {
    
    return (
        <div className={styles.container}>
            <LoginComponent />
        </div>
    );
}