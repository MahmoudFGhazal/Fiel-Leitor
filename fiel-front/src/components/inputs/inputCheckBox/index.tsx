'use client'
import styles from './inputCheckBox.module.css';

type InputCheckboxProps = {
    text?: string;
    onChange: (value: boolean) => void;
    disabled?: boolean; 
    checked: boolean;
};

export default function InputCheckBox({ text, onChange, disabled, checked }: InputCheckboxProps) {
    return (
        <label className={styles.inputContent}>
            <input
                type="checkbox"
                checked={checked}
                onChange={(e) => onChange(e.target.checked)}
                disabled={disabled}
            />
            {text && <span className={styles.text}>{text}</span>}
        </label>
    );
}