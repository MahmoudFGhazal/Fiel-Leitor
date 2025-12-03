'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import ActionButton from '@/components/buttonComponents/actionButton';
import { StatusSale } from '@/translate/base';
import { StatusSalePortuguese } from '@/translate/portuguses';
import { useState } from 'react';

interface Props {
    sale: SaleResponse
}

function normalizeStatus(status?: string | null): StatusSale | null {
    if (!status) return null;
    const upper = status.toUpperCase();
    if (upper in StatusSale) {
        return StatusSale[upper as keyof typeof StatusSale];
    }
    return null;
}

export default function TradeSaleLine({ sale }: Props) {
    const normalizedInitial = normalizeStatus(sale.statusSale?.status);
    const [status, setStatus] = useState<StatusSale | null>(normalizedInitial);

    const defineExchanged = async (id: number) => {
        try {
            const res = await api.put<ApiResponse>(
                `/sale/trade/delivered`,
                { params: { saleId: id } }
            );

            if (res.message) alert(res.message);
            setStatus(StatusSale.EXCHANGED);
        } catch (err) {
            console.error(err);
            alert('Erro ao marcar troca como entregue');
        }
    };

    const updateTradeStatus = async (id: number, confirm: boolean) => {
        try {
            const res = await api.put<ApiResponse>(
                `/sale/trade/status`,
                { params: { saleId: id, confirm } }
            );

            if (res.message) alert(res.message);

            setStatus(
                confirm
                    ? StatusSale.DECLINED
                    : StatusSale.EXCHANGE_AUTHORIZED
            );
        } catch (err) {
            console.error(err);
            alert('Erro ao atualizar troca');
        }
    };

    const ControlButtons = ({ id, status }: { id: number | null | undefined; status: StatusSale | null }) => {
        if (!id || !status) return null;

        switch (status) {
            case StatusSale.EXCHANGE_REQUESTED:
                return (
                    <>
                        <ActionButton
                            color="green"
                            label="Aceitar"
                            onClick={() => updateTradeStatus(id, true)}
                            dataCy="accept-button"
                        />
                        <ActionButton
                            color="red"
                            label="Rejeitar"
                            onClick={() => updateTradeStatus(id, false)}
                            dataCy="reject-button"
                        />
                    </>
                );

            case StatusSale.EXCHANGE_AUTHORIZED:
                return (
                    <ActionButton
                        color="green"
                        label="Troca autorizada"
                        onClick={() => defineExchanged(id)}
                        dataCy="trade-button"
                    />
                );

            default:
                return null;
        }
    };

    return (
        <>
            <td>{sale.id}</td>
            <td>{status ? StatusSalePortuguese[status] : "Status inválido"}</td>
            <td>
                R${sale.saleBooks?.reduce((acc, sb: any) => acc + sb.price, 0)}
            </td>
            <td>
                <ControlButtons id={sale.id} status={status} />
            </td>
        </>
    );
}
