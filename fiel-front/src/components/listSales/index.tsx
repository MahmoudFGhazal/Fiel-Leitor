'use client'
import { SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import { useEffect, useState } from 'react';
import OrderCard from '../order/orderSale';

export default function ListSales() {
    const { currentUser } = useGlobal();
    const [sales, setSales] = useState<SaleResponse[]>([]);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/sale/user', { params: { userId: currentUser } });

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data) as SaleResponse[] | null;

                if (!entities?.length) {
                    setSales([]);
                    return;
                }

                setSales(entities);
            } catch (err) {
                console.error("Erro ao carregar vendas", err);
            }
        }

        if (!currentUser) return;
        fetchData();
    }, [currentUser]);

    return (
        <div>
            {sales.map((sale) => (
                <OrderCard
                    key={sale.id}
                    sale={sale}
                />
            ))}
        </div>
    );
}
