'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { StatusSale } from '@/translate/base';
import { StatusSalePortuguese } from '@/translate/portuguses';
import { useEffect, useState } from 'react';
import styles from './finished.module.css';

function normalizeStatus(status?: string | null): StatusSale | null {
    if (!status) return null;
    const upper = status.toUpperCase();
    if (upper in StatusSale) {
        return StatusSale[upper as keyof typeof StatusSale];
    }
    return null;
}

export default function FinishedSales() {
    const [sales, setSales] = useState<SaleResponse[]>([]);

    useEffect(() => {
        const fetchCoupons = async () => {
            try {
                const res = await api.get<ApiResponse>(`/sale/finished`);
                if(!res.data) return;

                const entities = res.data.entities as SaleResponse[];
                if(!entities) return;

                setSales(entities);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar pedidos");
            } 
        };

        fetchCoupons();
    }, []);

    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <h2>Pedidos Finalizados</h2>
            </div>

            <table className={styles.saleTable}>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Status</th>
                        <th>Valor</th>
                    </tr>
                </thead>
                <tbody>
                    {sales.length === 0 ? (
                        <tr>
                            <td colSpan={3}>Nenhum pedido encontrado.</td>
                        </tr>
                    ) : (
                        sales.map((sale) => {
                            const status = normalizeStatus(sale?.statusSale?.status);
                            return (
                                <tr key={sale.id}>
                                    <td>{sale.id}</td>
                                    <td>{status ? StatusSalePortuguese[status] : "Status inválido"}</td>
                                    <td>R${sale?.saleBooks?.map(sb => sb.price)}</td>
                                </tr>
                            );
                        })
                    )}
                </tbody>
            </table>
        </div>
    );
}
