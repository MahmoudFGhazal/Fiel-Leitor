'use client'
import Image from 'next/image';
import styles from './boxInput.module.css';
import logo from '@/../public/whitelogo.png';

interface BoxInputProps {
    children: React.ReactNode;
}

export default function BoxInput({ children }: BoxInputProps) {
    return (
        <div className={styles.container}>
            <div className={styles.logoContent}>
                <Image src={logo} alt="Fiel Leitor Logo" className={styles.logo} />
            </div>
            <div className={styles.formContainer}>
                {children}
            </div>
        </div>
    );
}