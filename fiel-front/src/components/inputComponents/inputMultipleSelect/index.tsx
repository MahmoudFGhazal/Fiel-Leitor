'use client'
import { useEffect, useRef, useState } from 'react';
import styles from './inputSelect.module.css';

type Option = { value: string; label: string };

type InputSelectMultipleProps = {
    text?: string;
    onChange: (value: string[]) => void;
    disabled?: boolean; 
    options: Option[];
    value: string[];
    dataCy?: string;
};

export default function InputSelectMultiple({ text, onChange, disabled, options, value, dataCy }: InputSelectMultipleProps) {
    const [open, setOpen] = useState(false);
    const wrapperRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
                setOpen(false);
            }
        }
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    const toggleOption = (optValue: string) => {
        const updatedValue = value.includes(optValue)
            ? value.filter((v) => v !== optValue)
            : [...value, optValue];
        onChange(updatedValue);
    };

    const displaySelectedOptions = (vals: string[]) => {
        if (!vals || vals.length === 0) return '';
        return vals.length === 1 ? vals[0] : `${vals.slice(0, -1).join(', ')} e ${vals[vals.length - 1]}`;
    };

    return (
        <div className={styles.inputContent} ref={wrapperRef}>
            {text && <p className={styles.text}>{text}:</p>}
            <div
                tabIndex={0}
                className={styles.multiSelectBox}
                onClick={() => setOpen((prev) => !prev)}
                onKeyDown={(e) => {
                    if (e.key === 'Enter') setOpen((prev) => !prev);
                }}
                role="button"
                aria-haspopup="listbox"
                aria-expanded={open}
            >
                {displaySelectedOptions(value) || `Selecione ${text?.toLowerCase()}`}
            </div>
            {open && (
                <div className={styles.dropdown}>
                    {options.map((opt) => (
                        <label key={opt.value} className={styles.option}>
                            <input
                                type="checkbox"
                                checked={value.includes(opt.value)}
                                onChange={() => toggleOption(opt.value)}
                                disabled={disabled}
                                data-cy={dataCy}
                            />
                            {opt.label}
                        </label>
                    ))}
                </div>
            )}
        </div>
    );
}
