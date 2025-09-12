'use client'
import Link from 'next/link';
import styles from './buttonLink.module.css';
import { ReactNode } from 'react';

interface ButtonProps {
    text?: string;
    children?: ReactNode;
    disabled?: boolean;
    href: string; 
}

export default function ButtonLink({ text, children, disabled, href }: ButtonProps) {
    return (
        <Link
            href={disabled ? '#' : href}
            className={styles.button}
            aria-disabled={disabled}
            onClick={(e) => {
                if (disabled) {
                    e.preventDefault();
                }
            }}
        >
            {children ?? text}
        </Link>
    );
}
