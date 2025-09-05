'use client';
import { CardData } from "@/modal/cardModal";
import styles from './popUpCard.module.css';
import Input from "@/components/input";

interface Props {
    card: CardData;
    onChange: (field: keyof CardData, value: string) => void;
    disable: boolean;
}

export default function PopUpCard({ card, onChange, disable }: Props) {
    return (
        <div className={styles.formContainer}>
            <Input
                type="text"
                text="Apelido"
                value={card.nickname ?? ""}
                onChange={(val) => onChange('nickname', val)}
                disabled={disable}
            />
            <Input
                type="text"
                text="Número do Cartão"
                value={card.number ?? ""}
                onChange={(val) => onChange('number', val)}
                disabled={disable}
            />
            <Input
                type="text"
                text="Nome do Titular"
                value={card.holderName ?? ""}
                onChange={(val) => onChange('holderName', val)}
                disabled={disable}
            />
            <div className={styles.inputsContent}>
                <Input
                    type="text"
                    text="Validade"
                    value={card.expiration ?? ""}
                    onChange={(val) => onChange('expiration', val)}
                    disabled={disable}
                />
                <Input
                    type="text"
                    text="CVV"
                    value={card.cvv ?? ""}
                    onChange={(val) => onChange('cvv', val)}
                    disabled={disable}
                />
            </div>
        </div>
    );
}
