'use client'
import styles from './button.module.css';
import { ReactNode } from 'react';

interface ButtonProps {
    type: 'button' | 'submit' | 'reset',
    text?: string,
    onClick?: () => void,
    children?: ReactNode,
    disabled?: boolean,
}

export default function Button({ type, text, children, onClick, disabled }: ButtonProps) {
    return (
        <button type={type} className={styles.button} onClick={onClick} disabled={disabled}>
            {children ?? text}
        </button>
    );
}