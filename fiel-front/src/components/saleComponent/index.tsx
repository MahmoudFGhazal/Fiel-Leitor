'use client';
import { SaleBookRequest, SaleRequest } from '@/api/dtos/requestDTOs';
import { BookResponse, SaleResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import { useGlobal } from '@/context/GlobalContext';
import showToast from '@/utils/showToast';
import { usePathname, useSearchParams } from 'next/navigation';
import { useEffect, useMemo, useRef, useState } from 'react';
import Button from '../buttonComponents/button';
import CartItemsList from '../cartItemsList';
import SelectAddressMethod from '../selectAddressMethod';
import SelectPaymentMethod from '../selectPaymentMethod';
import CouponList from './couponList';
import styles from './saleComponent.module.css';

export interface ItemParam {
    bookId: number;
    quantity: number;
}

export interface SaleParam {
    items: ItemParam[];
    fromCart: boolean;
}

export interface SaleItemDetail {
    book: BookResponse;
    quantity: number;
}

const SALE_SS_KEY = 'sale_ctx_v1';

function isNavigationReload(): boolean {
    const entry = performance.getEntriesByType('navigation')[0] as PerformanceNavigationTiming | undefined;
    if (entry?.type) return entry.type === 'reload';

    return window.performance?.navigation?.type === 1;
}

function readStoredSale(itemsRaw: string | null): number | null {
    if (!itemsRaw) return null;
    try {
        const ss = sessionStorage.getItem(SALE_SS_KEY);
        if (!ss) return null;
        const parsed = JSON.parse(ss) as { items: string; saleId: number };
        return parsed.items === itemsRaw ? parsed.saleId : null;
    } catch {
        return null;
    }
}

function readStoredRaw(): { items: string; saleId: number } | null {
    try {
        const ss = sessionStorage.getItem(SALE_SS_KEY);
        return ss ? JSON.parse(ss) : null;
    } catch { return null; }
}

function writeStoredSale(itemsRaw: string | null, saleId: number) {
    if (!itemsRaw) return;
    try {
        sessionStorage.setItem(SALE_SS_KEY, JSON.stringify({ items: itemsRaw, saleId }));
    } catch {}
}

function clearStoredSale() {
    try {
        sessionStorage.removeItem(SALE_SS_KEY);
    } catch {}
}

export default function SaleComponent() {
    const searchParams = useSearchParams();
    const pathname = usePathname();
    const itemsParam = searchParams.get('items');

    const [items, setItems] = useState<SaleItemDetail[]>([]);
    const [total, setTotal] = useState(0);
    const [selectedAddressId, setSelectedAddressId] = useState<number | null>(null);
    const [selectedCards, setSelectedCards] = useState<{ card: number; percent: number }[]>([]);
    const [actualSale, setActualSale] = useState<number | null>(null);

    const { currentUser } = useGlobal();
    const ranRef = useRef(false);
    const leavingRef = useRef(false);
    const saleIdRef = useRef<number | null>(null);
    const [couponDiscount, setCouponDiscount] = useState(0);
    const [couponIds, setCouponIds] = useState<{ traderCouponIds: number[]; promotionalCouponId: number | null }>({
        traderCouponIds: [],
        promotionalCouponId: null,
    });

    const subtotal = useMemo(() => {
        return items.reduce((acc, item) => acc + Number(item.book.price ?? 0) * item.quantity, 0);
    }, [items]);

    const totalPrice = Math.max(0, subtotal - couponDiscount);

    async function cancelSaleReliable(saleId: number) {
        const payload: SaleRequest = {
            id: saleId,
            user: null,
            freight: null,
            deliveryDate: null,
            status: null,
            address: null,
            cards: null,
            books: null,
            traderCoupons: null,
            promotinalCoupon: null,
        };

        try {
            const res =await api.put<ApiResponse>('/sale/cancel', { data: payload });

            if (res.message) {
                alert(res.message);
                return;
            }
        } catch {}
    }

    const onLeavePage = async () => {
        if (leavingRef.current) return;
        const id = saleIdRef.current;
        if (!id) return;
        leavingRef.current = true;
        await cancelSaleReliable(id);
    };

    useEffect(() => {
        const handleBeforeUnload = () => { if (!isNavigationReload()) onLeavePage(); };
        const handlePageHide = (e: PageTransitionEvent) => { /* @ts-ignore */ if (e.persisted) return; if (!isNavigationReload()) onLeavePage(); };
        const handleVisibility = () => { if (document.visibilityState === 'hidden' && !isNavigationReload()) onLeavePage(); };

        window.addEventListener('beforeunload', handleBeforeUnload);
        window.addEventListener('pagehide', handlePageHide);
        document.addEventListener('visibilitychange', handleVisibility);
        
        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
            window.removeEventListener('pagehide', handlePageHide);
            document.removeEventListener('visibilitychange', handleVisibility);
        };
    }, []);

    useEffect(() => {
        return () => {
            if (!isNavigationReload()) onLeavePage();
        };
    }, [pathname]);

    useEffect(() => {
        const ss = readStoredRaw();
        const storedItems = ss?.items;
        const storedSaleId = ss?.saleId;

        if (storedSaleId && storedItems && itemsParam && storedItems !== itemsParam) {
        (async () => {
            try {
                const payload: SaleRequest = { 
                    id: storedSaleId, 
                    user: null, 
                    freight: null, 
                    deliveryDate: null, 
                    status: null, 
                    address: null,
                    cards: null, 
                    books: null, 
                    traderCoupons: null, 
                    promotinalCoupon: null 
                };

                await api.put<ApiResponse>('/sale/cancel', { data: payload });
            } catch (e) {
                console.error('Erro ao cancelar venda antiga por mudança de URL:', e);
            } finally {
                clearStoredSale();
                saleIdRef.current = null;
                setActualSale(null);
                ranRef.current = false; 
            }
        })();
        }
    }, [itemsParam]);

    useEffect(() => {
        if (ranRef.current) return;
        if (!currentUser) return;
        if (!itemsParam) return;

        const ssSaleId = readStoredSale(itemsParam);
        if (isNavigationReload() && ssSaleId) {
            saleIdRef.current = ssSaleId;
            setActualSale(ssSaleId);
            ranRef.current = true;
            return;
        }

        if (ssSaleId) {
            saleIdRef.current = ssSaleId;
            setActualSale(ssSaleId);
            ranRef.current = true;
            return;
        }

        async function fetchCreateSale(parsedItems: SaleParam) {
            if (!currentUser) {
                showToast('Nenhum usuário logado');
                return;
            }
            if (!parsedItems?.items?.length) return;

            try {
                const payload: SaleRequest = {
                    user: currentUser,
                    books: parsedItems.items.map(
                        (i): SaleBookRequest => ({
                            book: i.bookId,
                            quantity: i.quantity,
                            price: null,
                            sale: null,
                        })
                    ),
                    id: null,
                    freight: null,
                    deliveryDate: null,
                    status: null,
                    address: null,
                    cards: null,
                    traderCoupons: null,
                    promotinalCoupon: null,
                };

                const res = await api.post<ApiResponse>('/sale', { data: payload });
                
                if (res.message) {
                    alert(res.message);
                    return;
                }

                const entity = (res.data as { entity: SaleResponse | null }).entity;
                
                if (!entity?.id) {
                    showToast('Venda não criada');
                    window.location.href = '/';
                    return;
                }

                saleIdRef.current = entity.id;       
                setActualSale(entity.id);           
                writeStoredSale(itemsParam, entity.id);
                ranRef.current = true;
            } catch (err) {
                console.error('Erro ao criar venda:', err);
            }
        }

        try {
            const parsed: SaleParam = JSON.parse(itemsParam);
            fetchCreateSale(parsed);
        } catch (e) {
            console.error('Erro ao parsear itemsParam:', e);
        }
    }, [currentUser, itemsParam]);

    useEffect(() => {
        async function fetchBooks(parsedItems: SaleParam) {
            if (!parsedItems?.items?.length) return;

            try {
                const bookIds = parsedItems.items.map((i) => i.bookId);
                const res = await api.get<ApiResponse>('/book/list', { params: { bookIds } });

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data) as BookResponse[];

                const detailed: SaleItemDetail[] = parsedItems.items
                    .map((p) => {
                        const book = entities.find((e) => e.id === p.bookId);
                        if (!book) return null;
                        return { book, quantity: p.quantity };
                    })
                    .filter((x): x is SaleItemDetail => x !== null);

                setItems(detailed);
            } catch (err) {
                console.error('Erro ao buscar detalhes dos livros:', err);
            }
        }

        if (itemsParam) {
            try {
                const parsed: SaleParam = JSON.parse(itemsParam);
                fetchBooks(parsed);
            } catch (err) {
                console.error('Erro ao parsear itemsParam:', err);
            }
        }
    }, [itemsParam]);

     useEffect(() => {
        const totalValue = items.reduce((acc, item) => acc + Number(item.book.price) * item.quantity, 0);
        setTotal(totalValue);
    }, [items]);

    const sendSale = async () => {
        if (!currentUser) {
            alert('Nenhum usuario logado');
            return;
        }

        const id = saleIdRef.current ?? actualSale;
        if (!id) {
            showToast('Venda ainda não criada.');
            return;
        }

        try {
            const req: SaleRequest = {
                id,
                user: currentUser,
                address: selectedAddressId,
                cards: selectedCards.map((c) => ({
                sale: null,
                card: c.card,
                percent: c.percent / 100,
                price: null,
                })),
                books: null,
                freight: null,
                deliveryDate: null,
                status: null,
                traderCoupons: couponIds.traderCouponIds,
                promotinalCoupon: couponIds.promotionalCouponId,
            };

            const res = await api.put<ApiResponse>('/sale/payment', { data: req });
            if (res.message) {
                alert(res.message);
                return;
            }

            clearStoredSale(); 
            showToast('Pedido Enviado com Sucesso');
            window.location.href = '/';
        } catch (err) {
            console.error('Erro ao carregar carrinho', err);
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.content}>
                <div>
                    <SelectAddressMethod onSelect={setSelectedAddressId} />
                    <SelectPaymentMethod purchaseTotal={total} onSelect={setSelectedCards} />
                </div>

                <div className={styles.cartItemsContainer}>
                    <h3>Itens</h3>
                    <CartItemsList
                        items={items.map(i => ({
                            id: i.book.id ?? 0,
                            name: i.book.name ?? '',
                            price: i.book.price ?? 0,
                            quantity: i.quantity
                        }))}
                    />

                    <div className={styles.total}>
                        <CouponList onDiscountChange={setCouponDiscount} onCouponsChange={setCouponIds} />

                        <div className={styles.totalsBlock}>
                            <div className={styles.totalsRow}>
                                <span>Subtotal: </span>
                                <strong>R$ {subtotal.toFixed(2)}</strong>
                            </div>
                            <div className={styles.totalsRow}>
                                <span>Descontos: </span>
                                <strong>R$ {couponDiscount.toFixed(2)}</strong>
                            </div>
                            <div className={styles.totalsRowFinal}>
                                <span>Total: </span>
                                <strong>R$ {totalPrice.toFixed(2)}</strong>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className={styles.buttonContent}>
                <Button
                    type='button'
                    text='Finalizar Compra'
                    onClick={() => sendSale()}
                    dataCy='finalize-purchase-button'
                />
            </div>
        </div>
    );
}
