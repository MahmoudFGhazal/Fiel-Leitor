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

    const acceptRequest = async (id: number) => {
        try {
            const res = await api.put<ApiResponse>(`/sale/trade/status`, {
                params: { saleId: id, confirm: true }
            });

            if (res.message) alert(res.message);

            setStatus(StatusSale.DECLINED);
        } catch (err) {
            console.error(err);
            alert('Erro ao carregar pedidos');
        }
    };

    const refuseRequest = async (id: number) => {
        try {
            const res = await api.put<ApiResponse>(`/sale/trade/status`, {
                params: { saleId: id, confirm: false }
            });

            if (res.message) alert(res.message);

            setStatus(StatusSale.EXCHANGE_AUTHORIZED);
        } catch (err) {
            console.error(err);
            alert('Erro ao carregar pedidos');
        }
    };

    const defineExchanged = async (id: number) => {
        try {
            const res = await api.put<ApiResponse>(`/sale/trade/delivered`, {
                params: { saleId: id }
            });

            if (res.message) alert(res.message);

            setStatus(StatusSale.EXCHANGED);
        } catch (err) {
            console.error(err);
            alert('Erro ao carregar pedidos');
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
                            onClick={() => acceptRequest(id)}
                        />
                        <ActionButton
                            color="red"
                            label="Rejeitar"
                            onClick={() => refuseRequest(id)}
                        />
                    </>
                );

            case StatusSale.EXCHANGE_AUTHORIZED:
                return (
                    <>
                        <ActionButton
                            color="green"
                            label="Trocado"
                            onClick={() => defineExchanged(id)}
                        />
                    </>
                );

            default:
                return null;
        }
    };

    return (
        <>
            <td>{sale.id}</td>
            <td>{status ? StatusSalePortuguese[status] : "Status inválido"}</td>
            <td>R${sale?.saleBooks?.map((sb: any) => sb.price)}</td>
            <td>
                <ControlButtons id={sale.id} status={status} />
            </td>
        </>
    );
}
