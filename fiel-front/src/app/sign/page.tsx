'use server'
import SignInComponent from '@/components/signIn';
import styles from './page.module.css';

export default async function SignIn() {
    return (
        <div className={styles.container}>
            <SignInComponent />
        </div>
    );
}