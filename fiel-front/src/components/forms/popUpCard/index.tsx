// src/components/forms/PopUpCard.tsx
'use client';
import { CardRequest } from '@/api/dtos/requestDTOs';
import Input from "@/components/input";
import styles from './popUpCard.module.css';

interface Props {
    card: CardRequest;
    onChange: (field: keyof CardRequest, value: string) => void;
    disable: boolean;
}

function maskPanForDisplay(panRaw: string) {
    const clean = (panRaw ?? '').replace(/\D/g, '').slice(0, 16); // <-- limita aqui
    if (!clean) return '';
    return clean.replace(/(\d{4})(?=\d)/g, '$1 ');
}

function formatExpForDisplay(expRaw: string) {
    const clean = (expRaw ?? '').replace(/\D/g, '');
    if (clean.length === 0) return '';
    const month = clean.slice(0, 2);
    const year = clean.slice(2, 4);
    return year ? `${month}/${year}` : month;
}

export default function PopUpCard({ card, onChange, disable }: Props) {
    const pan = (card as any).pan ?? '';
    const expInput = (card as any).expInput ?? '';

    return (
        <div className={styles.formContainer}>
            <Input
                type="text"
                text="Nome do Titular"
                value={card.holder ?? ""}
                onChange={(val) => onChange('holder', val)}
            />

            <Input
                type="text"
                text="Número do Cartão"
                value={maskPanForDisplay(pan)}
                onChange={(val) => {
                    const cleaned = (val ?? '').replace(/\s+/g, '');
                    onChange('bin', cleaned);
                }}
                disabled={disable}
            />

            <Input
                type="text"
                text="Validade"
                value={formatExpForDisplay(expInput)}
                onChange={(val) => {
                    const cleaned = (val ?? '').replace(/\D/g, '').slice(0, 4); 
                    onChange('expMonth', cleaned);
                }}
                disabled={disable}
            />
        </div>
    );
}
