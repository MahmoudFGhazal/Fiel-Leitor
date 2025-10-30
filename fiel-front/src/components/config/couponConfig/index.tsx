'use client'
import { UserRequest } from '@/api/dtos/requestDTOs';
import { UserResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import ActionButton from '@/components/buttonComponents/actionButton';
import UserFormEdit from '@/components/forms/userFormEdit';
import { useGlobal } from '@/context/GlobalContext';
import { useEffect, useState } from "react";
import styles from './couponConfig.module.css';
import { toRequestUser } from '@/utils/convertDTOs';

export default function CouponConfig() {
    const { currentUser } = useGlobal();
    const [coupons, setCoupons] = useState<TraderCouponResponse>();

    useEffect(() => {
        const fetchCoupons = async () => {
            try {
                const res = await api.get<ApiResponse>(`/traderCoupon/user`, { params: { userId: currentUser }});
                if(!res.data) {
                    return;
                }

                const entities = res.data.entities as TraderCouponResponse;

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
    
    const handleChange = (field: string, value: string) => {
        setEditedUser(prev => {
            if (!prev) return prev;

            if (field === "gender") {
                return {
                    ...prev,
                    gender: Number(value),
                };
            }

            return {
                ...prev,
                [field as keyof UserRequest]: value,
            };
        });
    };

    return (
        <div>
            
        </div>
    );
} 