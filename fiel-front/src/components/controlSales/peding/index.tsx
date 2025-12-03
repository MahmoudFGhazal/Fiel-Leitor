'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import ActionButton from '@/components/buttonComponents/actionButton';
import { StatusSale } from '@/translate/base';
import { StatusSalePortuguese } from '@/translate/portuguses';
import { useEffect, useState } from 'react';
import styles from './peding.module.css';
import PopUpInTransit from './popUpInTransit';


export interface ReqInTrasit {
    id: number,
    deliveryDate: string,
    freight: number
}

function normalizeStatus(status?: string | null): StatusSale | null {
    if (!status) return null;

    const upper = status.toUpperCase();
    if (upper in StatusSale) {
        return StatusSale[upper as keyof typeof StatusSale];
    }

    return null;
}

export default function PedingSales() {
    const [sales, setSales] = useState<SaleResponse[]>([]);
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [idInTrasit, setIdInTransit] = useState<number | null>(null);

    useEffect(() => {
        const fetchCoupons = async () => {
            try {
                const res = await api.get<ApiResponse>(`/sale/peding`);
                if (!res.data) return;

                const entities = res.data.entities as SaleResponse[];
                if (!entities) return;

                setSales(entities);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar pedidos");
            }
        };

        fetchCoupons();
    }, []);

    async function defineInTrasit(req: ReqInTrasit) {
        try {
            const res = await api.put<ApiResponse>(`/sale/transit`, {
                params: {
                    saleId: req.id,
                    deliveryDate: req.deliveryDate,
                    freight: req.freight
                }
            });

            if (res.message) alert(res.message);

            setSales(prev =>
                prev.map(sale => sale.id === req.id ? {
                    ...sale,
                    statusSale: { status: StatusSale.IN_TRANSIT },
                    deliveryDate: req.deliveryDate,
                    freight: req.freight
                } as unknown as SaleResponse : sale)
            );

            setIsOpen(false);
            setIdInTransit(null);
            alert("Venda atualizada com sucesso");

        } catch (err) {
            console.error(err);
            alert("Erro ao mudar status pedidos");
        }
    }

    async function defineDelivered(id: number) {
        try {
            const res = await api.put<ApiResponse>(`/sale/delivered`, {
                params: { saleId: id }
            });

            if (!res.data) return;
            if (res.message) alert(res.message);

            setSales(prev => prev.filter(sale => sale.id !== id));
            alert("Venda atualizada com sucesso");

        } catch (err) {
            console.error(err);
            alert("Erro ao mudar status pedidos");
        }
    }

    const nextStage = async (id: number | null, status: string | null) => {
        if (!id) return alert("Erro ao pegar o ID");

        const normalized = normalizeStatus(status);

        switch (normalized) {
            case StatusSale.APPROVED:
                setIdInTransit(id);
                setIsOpen(true);
                break;

            case StatusSale.IN_TRANSIT:
                await defineDelivered(id);
                break;

            default:
                alert("Status inválido");
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <h2>Pedidos Pendentes</h2>
            </div>

            <table className={styles.saleTable}>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Status</th>
                        <th>Valor</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {sales.length === 0 ? (
                        <tr>
                            <td colSpan={4}>Nenhum pedido encontrado.</td>
                        </tr>
                    ) : (
                        sales.map(sale => {
                            const norm = normalizeStatus(sale?.statusSale?.status);
                            return (
                                <tr key={sale.id}>
                                    <td>{sale.id}</td>
                                    <td>{norm ? StatusSalePortuguese[norm] : "Desconhecido"}</td>
                                    <td>R${sale.saleBooks?.map(sb => sb.price)}</td>
                                    <td>
                                        <ActionButton
                                            color='green'
                                            label='Passar'
                                            onClick={() => nextStage(sale.id, sale?.statusSale?.status ?? null)}
                                            dataCy='pass-button'
                                        />
                                    </td>
                                </tr>
                            );
                        })
                    )}
                </tbody>
            </table>

            {isOpen && (
                <div className={styles.modalBackdrop}>
                    <div className={styles.modal}>
                        <PopUpInTransit
                            id={idInTrasit ?? 0}
                            onSubmit={defineInTrasit}
                            onClose={() => {
                                setIsOpen(false);
                                setIdInTransit(null);
                            }}
                        />
                    </div>
                </div>
            )}
        </div>
    );
}
