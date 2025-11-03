'use client'
import { TraderCouponResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import { useEffect, useState } from "react";
import styles from './couponConfig.module.css';

export default function CouponConfig() {
    const { currentUser } = useGlobal();
    const [coupons, setCoupons] = useState<TraderCouponResponse[]>([]);

    useEffect(() => {
        const fetchCoupons = async () => {
            try {
                const res = await api.get<ApiResponse>(`/traderCoupon/user`, { params: { userId: currentUser }});
                if(!res.data) {
                    return;
                }

                const entities = res.data.entities as TraderCouponResponse[];

                if(!entities) {
                    return;
                }

                setCoupons(entities);
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

            <table className={styles.couponTable}>
                <thead>
                    <tr>
                        <th>Código</th>
                    </tr>
                </thead>
                <tbody>
                    {coupons.length === 0 ? (
                        <tr>
                            <td colSpan={3}>Nenhum cupom encontrado.</td>
                        </tr>
                    ) : (
                        coupons.map((coupon) => (
                            <tr key={coupon.id}>
                                <td>{coupon.code}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
} 