'use client'
import { KeyboardEvent } from 'react';
import styles from './inputText.module.css';

type InputTextProps = {
    text?: string;
    uptext?: boolean
    onChange: (value: any) => void;
    disabled?: boolean; 
    type: 'text' | 'email' | 'password' | 'number' | 'date';
    value: string;
    dataCy?: string;
    onKeyDown?: (e: KeyboardEvent<HTMLInputElement>) => void;
};

export default function InputText({
    text, uptext = true, onChange, disabled, type, value, dataCy, onKeyDown
}: InputTextProps) {
    return (
        <div className={styles.inputContent}>
            {(uptext && text) && <p className={styles.text}>{text}:</p>}
            <input
                className={styles.input}
                type={type}
                value={value}
                onChange={(e) => onChange(e.target.value)}
                onKeyDown={onKeyDown} 
                disabled={disabled}
                placeholder={text}
                data-cy={dataCy}
            />
        </div>
    );
}