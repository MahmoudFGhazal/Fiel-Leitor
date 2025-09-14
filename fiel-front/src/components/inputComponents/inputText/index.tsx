'use client'
import styles from './inputText.module.css';

type InputTextProps = {
    text?: string;
    onChange: (value: any) => void;
    disabled?: boolean; 
    type: 'text' | 'email' | 'password' | 'number' | 'date';
    value: string;
};

export default function InputText({ text, onChange, disabled, type, value }: InputTextProps) {
    return (
        <div className={styles.inputContent}>
            {text && <p className={styles.text}>{text}:</p>}
            <input
                className={styles.input}
                type={type}
                value={value}
                onChange={(e) => onChange(e.target.value)}
                disabled={disabled}
                placeholder={text}
            />
        </div>
    );
}