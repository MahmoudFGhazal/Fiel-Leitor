'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import { StatusSalePortuguese } from '@/translate/portuguses';
import { normalizeStatus } from '@/utils/normalize';
import { useEffect, useMemo, useState } from 'react';
import OrderItem from '../orderItem';
import styles from './orderCard.module.css';

function formatDate(d: Date | string | null | undefined) {
    if (!d) return '';
    const dt = d instanceof Date ? d : new Date(d);
    return isNaN(dt.getTime()) ? '' : dt.toLocaleDateString('pt-BR');
}

export default function OrderSale({ sale }: { sale: SaleResponse }) {
    const [total, setTotal] = useState<number>(0);

    const [currentStatus, setCurrentStatus] = useState<string | null>(
        sale.statusSale?.status ?? null
    );

    const translatedStatus = (() => {
        const normalized = normalizeStatus(currentStatus);
        return normalized ? StatusSalePortuguese[normalized] : "Status indefinido";
    })();

    useEffect(() => {
        if (!sale?.saleBooks?.length) return;

        const sum = sale.saleBooks.reduce((acc, book) => acc + (book.price ?? 0), 0);
        setTotal(sum);
    }, [sale]);

    const requestTrade = async () => {
        try {
            const res = await api.put<ApiResponse>('/sale/trade/request', { params: { saleId: sale.id } });
            if (res.message) {
                alert(res.message);
                return;
            }
            
            setCurrentStatus('EXCHANGE_REQUESTED');
            alert("Requisição enviada");
        } catch (err) {
            console.error("Erro ao carregar vendas", err);
        }
    }

    const createdAtStr = useMemo(() => formatDate(sale?.createdAt), [sale?.createdAt]);

    const canRequestTrade = currentStatus === 'DELIVERED';

    return (
        <div className={styles.orderSale}>
            <div className={styles.orderHeader}>
                <div>
                    <p><strong>Pedido realizado:</strong> {createdAtStr}</p>
                    <p><strong>Total:</strong> R$ {total.toFixed(2)}</p>
                    <p className={styles.statusLine}>
                        <strong>Status:</strong> {translatedStatus}
                        {canRequestTrade && (
                            <Button
                                text='Pedir Troca'
                                type="button"
                                onClick={requestTrade}
                                dataCy='request-button'
                            />
                        )}
                    </p>
                </div>
                <div>
                    <p><strong>Pedido nº</strong> {sale.id}</p>
                </div>
            </div>

            <div className={styles.itemsList}>
                {sale.saleBooks?.map((saleBook, index) => (
                    <OrderItem 
                        key={saleBook.book?.id ?? `fallback-${index}`} 
                        book={saleBook} 
                    />
                ))}
            </div>
        </div>
    );
}
