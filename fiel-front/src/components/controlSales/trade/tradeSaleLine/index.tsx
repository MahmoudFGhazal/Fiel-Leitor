'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import ActionButton from '@/components/buttonComponents/actionButton';
import { useState } from 'react';

interface Props {
    sale: SaleResponse
}

export default function TradeSaleLine({ sale }: Props) {
    const [status, setStatus] = useState<string>(sale.statusSale?.status ?? "");
    
    const acceptRequest = async (id: number) => {
        try {
            const res = await api.put<ApiResponse>(`/sale/trade/status`, {
                params: { saleId: id, confirm: true }
            });

            if (res.message) {
                alert(res.message);
            }

            setStatus('DECLINED');
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

            if (res.message) {
                alert(res.message);
            }

            setStatus('EXCHANGE_AUTHORIZED');
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

            if (res.message) {
                alert(res.message);
            }

            setStatus('EXCHANGED');
        } catch (err) {
            console.error(err);
            alert('Erro ao carregar pedidos');
        }
    };

    // componente interno
    const ControlButtons = ({
        id,
        status
    }: {
        id: number | null | undefined;
        status: string | null | undefined;
    }) => {
        if (!id) return null;

        switch (status) {
            case 'EXCHANGE_REQUESTED':
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
            case 'EXCHANGE_AUTHORIZED':
                return (
                <>
                    <ActionButton
                    color="green"
                    label="Entregue"
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
            <td>{status}</td>
            <td>R${sale?.saleBooks?.map((sb: any) => sb.price)}</td>
            <td>
                <ControlButtons id={sale.id} status={status} />
            </td>
        </>                              
    );
}
