'use client';
import Button from '@/components/buttonComponents/button';
import InputText from '@/components/inputComponents/inputText';
import { useState } from 'react';
import { ReqInTrasit } from '..';
import styles from './popUpInTrasit.module.css';

interface Props {
    onSubmit: (data: ReqInTrasit) => void;
    id: number;
    onClose?: () => void;
}

export default function PopUpInTransit({ onSubmit, id, onClose }: Props) {
    const [form, setForm] = useState<ReqInTrasit>({
        id,
        deliveryDate: '',
        freight: 0,
    });

    const handleChange = (field: keyof ReqInTrasit, value: string) => {
        if (field === 'freight') {
        let v = value.replace(/[^0-9.]/g, '');

        const firstDot = v.indexOf('.');
        if (firstDot !== -1) {
            const before = v.slice(0, firstDot + 1);
            const after = v.slice(firstDot + 1).replace(/\./g, '');
            v = before + after;
        }

        if (v.includes('.')) {
            const [int, dec] = v.split('.');
            v = int + '.' + dec.slice(0, 2);
        }

        setForm((prev) => ({
                ...prev,
                freight: v === '' ? 0 : Number(v),
            }));
            return;
        }

        setForm((prev) => ({
        ...prev,
        [field]: value,
        }));
    };

    const handleSubmit = () => {
        if (!form.deliveryDate) {
            alert('Informe a data de entrega');
            return;
        }

        if (!form.freight) {
            alert('Informe o frete');
            return;
        }

        onSubmit(form);
    };

    return (
        <div className={styles.formContainer}>
            <h3>Colocar em trânsito</h3>

            <div className={styles.inputsContent}>
                <InputText
                    type="date"
                    text="Data de entrega"
                    value={form.deliveryDate}
                    onChange={(val) => handleChange('deliveryDate', val)}
                    dataCy="delivery-date-text"
                    />
                    <InputText
                    type="number"
                    text="Frete (R$)"
                    value={form.freight.toString()}
                    onChange={(val) => handleChange('freight', val)}
                    dataCy="freight-text"
                />
            </div>

            <div className={styles.actions}>
                <Button 
                    onClick={handleSubmit} 
                    type={'submit'}     
                    text='Confirmar'          
                />

                {onClose && (
                    <Button 
                        onClick={onClose}
                        type={'button'}   
                        text='Cancelar'
                />
                )}
            </div>
        </div>
    );
}
