'use client';
import { PromotionalCouponResponse, TraderCouponResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import InputText from '@/components/inputComponents/inputText';
import { useEffect, useState } from 'react';
import styles from './couponList.module.css';

type CuponListProps = {
  onDiscountChange?: (totalDiscount: number) => void; 
};

function sameTraderCoupon(a: TraderCouponResponse, b: TraderCouponResponse) {
  return (
    (a?.id != null && b?.id != null && a.id === b.id) ||
    (!!a?.code && !!b?.code && a.code === b.code)
  );
}

function hasTraderCoupon(
    list: TraderCouponResponse[] | undefined,
    entity: TraderCouponResponse
): boolean {
    return !!list?.some(tc => sameTraderCoupon(tc, entity));
}

export default function CouponList({ onDiscountChange }: CuponListProps) {
    const [couponCode, setCouponCode] = useState<string>("");
    const [promotionalCoupon, setPromotionalCoupon] = useState<PromotionalCouponResponse>();
    const [traderCoupons, setTraderCoupons] = useState<TraderCouponResponse[]>([]);

    useEffect(() => {
        const traderSum = traderCoupons.reduce((acc, c) => acc + Number(c?.value ?? 0), 0);
        const promo = Number(promotionalCoupon?.value ?? 0);
        const totalDiscount = traderSum + promo;
        onDiscountChange?.(totalDiscount);
    }, [traderCoupons, promotionalCoupon, onDiscountChange]);

    const updateCoupon = async () => {
        if(!couponCode) {
            alert("Nenhum Cupom Inserido");
            return;
        }

        try {
            const resTrader = await api.get<ApiResponse>('/traderCoupon/check', { params: { code: couponCode } });
            if (resTrader.message) {
                alert(resTrader.message);
                return;
            }

            const traderEntity = resTrader.data?.entity as TraderCouponResponse;

            if (traderEntity) {
                if(traderEntity.used) {
                    alert("Cupom já utilizado");
                    return;
                }

                const alreadyInList = hasTraderCoupon(traderCoupons, traderEntity);
                if (alreadyInList) {
                    alert("Esse cupom já foi adicionado");
                    setCouponCode('');
                    return;
                }

                setTraderCoupons(prev => [...prev, traderEntity]);
                setCouponCode('');
                return; 
            }

            const resPromotional = await api.get<ApiResponse>('/promotionalCoupon/check', { params: { code: couponCode } });

            if (resPromotional?.message && !resPromotional?.data?.entity) {
                alert(resPromotional.message || 'Cupom inválido');
                return;
            }

            const promotionalEntity = resPromotional?.data?.entity as PromotionalCouponResponse;
            if (!promotionalEntity) {
                alert('Cupom inválido');
                return;
            }

            if(promotionalEntity.used) {
                alert("Cupom já utilizado");
                return;
            }

            if (promotionalCoupon) {
                alert('Só é possível inserir um cupom promocional por vez.');
                return;
            }

            setPromotionalCoupon(promotionalEntity);
            setCouponCode('');
        } catch (err) {
            console.error('Erro ao pegar cupomn', err);
        }
    };

    return (
        <div className={styles.couponSection}>
            <div>
                <h4>Usar Cupom</h4>
                <InputText
                    type="text"
                    text="Digite o código do cupom"
                    uptext={false}
                    value={couponCode}
                    onChange={setCouponCode}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter') {
                            e.preventDefault();
                            updateCoupon();
                        }
                    }}
                />
            </div>

            {traderCoupons.map((c, idx) => (
                <div
                    key={`trader-${c?.id ?? c?.code ?? idx}`}
                    className={styles.row}
                >
                    <div className={styles.cellName}>{c?.code ?? '-'}</div>
                    <div className={styles.cellType}>Trader</div>
                    <div className={styles.cellValue}>R$ {Number(c?.value ?? 0).toFixed(2)}</div>
                </div>
            ))}

            {promotionalCoupon && (
                <div
                    key={`promo-${promotionalCoupon?.id ?? promotionalCoupon?.code ?? '0'}`}
                    className={styles.row}
                >
                    <div className={styles.cellName}>{promotionalCoupon?.code ?? '-'}</div>
                    <div className={styles.cellType}>Promocional</div>
                    <div className={styles.cellValue}>R$ {Number(promotionalCoupon?.value ?? 0).toFixed(2)}</div>
                </div>
            )}

            {!promotionalCoupon && traderCoupons.length === 0 && (
                <div className={styles.emptyHint}>Nenhum cupom aplicado.</div>
            )}
        </div>
    );
}
