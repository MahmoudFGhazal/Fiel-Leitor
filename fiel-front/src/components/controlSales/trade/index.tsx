'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { useEffect, useState } from 'react';
import styles from './trade.module.css';


export default function TradeSales() {
    const [sales, setSales] = useState<SaleResponse[]>([]);

    useEffect(() => {
        const fetchCoupons = async () => {
            try {
                const res = await api.get<ApiResponse>(`/sale/trade`);
                if(!res.data) {
                    return;
                }

                const entities = res.data.entities as SaleResponse[];

                if(!entities) {
                    return;
                }

                setSales(entities);
            } catch (err) {
                console.error(err);
                alert("Erro ao carregar cupons");
            } 
        };

        fetchCoupons();
    }, []);

    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <h2>Cupons</h2>
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
                            <td colSpan={3}>Nenhum cupom encontrado.</td>
                        </tr>
                    ) : (
                        sales.map((sale) => (
                            <tr key={sale.id}>
                                <td>{sale.id}</td>
                                <td>{sale?.statusSale?.status}</td>
                                <td>R${sale?.saleBooks?.map((sb: any) => sb.price)}</td>
                                <td>Botão</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
}