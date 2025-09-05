'use client'
import styles from './input.module.css';
import { useEffect, useRef, useState } from 'react';

type BaseProps = {
    text?: string;
    onChange: (value: any) => void;
    disabled?: boolean; 
};

type TextInputProps = BaseProps & {
    type: 'text' | 'email' | 'password' | 'number' | 'date';
    value: string;
};

type CheckboxProps = BaseProps & {
    type: 'checkbox';
    checked: boolean;
};

type SelectProps = BaseProps & {
    options: { value: string; label: string }[];
    value: string | string[];
    multiple?: boolean;
};

type InputProps = TextInputProps | CheckboxProps | SelectProps;

export default function Input(props: InputProps) {
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

    const displaySelectedOptions = (vals: string[] | string | undefined) => {
        if (Array.isArray(vals)) {
            return vals.length === 0
                ? ''
                : vals.length === 1
                ? vals[0]
                : `${vals.slice(0, -1).join(', ')} e ${vals[vals.length - 1]}`;
        }
        return vals || '';
    };

    if ('type' in props && props.type === 'checkbox') {
        return (
            <div className={styles.content}>
                {props.text && <p className={styles.text}>{props.text}:</p>}
                <input
                    type="checkbox"
                    checked={props.checked}
                    onChange={(e) => props.onChange(e.target.checked)}
                    disabled={props.disabled}
                />
            </div>
        );
    }

    if ('options' in props) {
        const { options, value, multiple, text, onChange } = props;

        const toggleOption = (optValue: string) => {
        if (!Array.isArray(value)) return;
            const updatedValue = value.includes(optValue)
                ? value.filter((v) => v !== optValue)
                : [...value, optValue];
            onChange(updatedValue);
        };

        if (multiple) {
            return (
                <div className={styles.content} ref={wrapperRef}>
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
                            checked={Array.isArray(value) && value.includes(opt.value)}
                            onChange={() => toggleOption(opt.value)}
                            disabled={props.disabled}
                        />
                        {opt.label}
                        </label>
                    ))}
                    </div>
                )}
                </div>
            );
        }

    return (
        <div className={styles.content}>
            {text && <p className={styles.text}>{text}:</p>}
            <select
                value={typeof value === 'string' ? value : ''}
                className={styles.input}
                onChange={(e) => onChange(e.target.value)}
                disabled={props.disabled}
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

    if ('type' in props && typeof props.value === 'string') {
        return (
            <div className={styles.content}>
                {props.text && <p className={styles.text}>{props.text}:</p>}
                <input
                    type={props.type}
                    value={props.value}
                    onChange={(e) => props.onChange(e.target.value)}
                    placeholder={props.text}
                    className={styles.input}
                    disabled={props.disabled}
                />
            </div>
        );
    }

    return null;
}