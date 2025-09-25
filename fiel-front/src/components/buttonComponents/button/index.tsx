'use client'
import { ReactNode } from 'react';
import styles from './button.module.css';

interface ButtonProps {
    type: 'button' | 'submit' | 'reset',
    text?: string,
    onClick?: () => void,
    children?: ReactNode,
    disabled?: boolean,
    dataCy?: string
}

export default function Button({ type, text, children, onClick, disabled, dataCy }: ButtonProps) {
    return (
        <button type={type} className={styles.button} onClick={onClick} disabled={disabled} data-cy={dataCy}>
            {children ?? text}
        </button>
    );
}