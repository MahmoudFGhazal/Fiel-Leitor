'use client'
import styles from './inputSelect.module.css';

type Option = { value: string; label: string };

type InputSelectProps = {
    text?: string;
    onChange: (value: string) => void;
    disabled?: boolean; 
    options: Option[];
    value: string;
};

export default function InputSelect({ text, onChange, disabled, options, value }: InputSelectProps) {
    return (
        <div className={styles.inputContent}>
            {text && <p className={styles.text}>{text}:</p>}
            <select
                value={value}
                className={styles.input}
                onChange={(e) => onChange(e.target.value)}
                disabled={disabled}
            >
                <option value="">{`Selecione ${text?.toLowerCase()}`}</option>
                    {options.map((opt) => (
                        <option key={opt.value} value={opt.value}>
                            {opt.label}
                        </option>
                    ))}
            </select>
        </div>
    );
}