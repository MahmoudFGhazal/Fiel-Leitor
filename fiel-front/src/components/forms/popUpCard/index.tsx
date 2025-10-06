'use client';
import { CardRequest } from '@/api/dtos/requestDTOs';
import Input from "@/components/input";
import styles from './popUpCard.module.css';

interface Props {
    card: CardRequest;
    onChange: (field: keyof CardRequest, value: string) => void;
    disable: boolean;
}

export default function PopUpCard({ card, onChange, disable }: Props) {
    return (
        <div className={styles.formContainer}>
            <Input
                type="text"
                text="Nome do Titular"
                value={card.holder ?? ""}
                onChange={(val) => onChange('holder', val)}
                disabled={disable}
            />
            <Input
                type="text"
                text="Número"
                value={card.bin ?? ""}
                onChange={(val) => onChange('bin', val)}
                disabled={disable}
            />
            <Input
                type="text"
                text="Validade"
                value={card.expMonth ?? ""}
                onChange={(val) => onChange('expMonth', val)}
                disabled={disable}
            />
        </div>
    );
}
