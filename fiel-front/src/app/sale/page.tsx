'use client'
import { SaleRequest } from '@/api/dtos/requestDTOs';
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import CartItemsList from '@/components/cartItemsList';
import SelectAddressMethod from '@/components/selectAddressMethod';
import SelectPaymentMethod from '@/components/selectPaymentMethod';
import { useGlobal } from '@/context/GlobalContext';
import showToast from '@/utils/showToast';
import { useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import styles from './page.module.css';

export interface SaleItemParam {
    bookId: number;
    quantity: number;
    fromCart: boolean;
}

export interface SaleItemDetail {
    book: BookResponse;
    quantity: number;
    fromCart: boolean;
}

export default function Sale() {
    const searchParams = useSearchParams();
    const itemsParam = searchParams.get('items');
    const [items, setItems] = useState<SaleItemDetail[]>([]);
    const [total, setTotal] = useState(0);
    const [selectedAddressId, setSelectedAddressId] = useState<number | null>(null);
    const [selectedCards, setSelectedCards] = useState<{ card: number; percent: number }[]>([]);
    const { currentUser } = useGlobal();

    useEffect(() => {
        async function fetchBooks(parsedItems: SaleItemParam[]) {
            try {
                const bookIds = parsedItems.map(i => i.bookId);
                const res = await api.get<ApiResponse>('/book/list', { params: { bookIds: bookIds } });

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data) as BookResponse[];

                const detailed = parsedItems.map(p => ({
                    book: entities.find(e => e.id === p.bookId)!,
                    quantity: p.quantity,
                    fromCart: p.fromCart
                }));

                setItems(detailed);
            } catch (err) {
                console.error("Erro ao buscar detalhes dos livros:", err);
            }
        }

        if (itemsParam) {
            try {
                const parsed: SaleItemParam[] = JSON.parse(itemsParam);
                fetchBooks(parsed);
            } catch (err) {
                console.error("Erro ao parsear itemsParam:", err);
            }
        }
    }, [itemsParam]);

    useEffect(() => {
        const totalValue = items.reduce((acc, item) => acc + (Number(item.book.price) * item.quantity), 0);
        setTotal(totalValue);
    }, [items]);

    const handleIncrease = (bookId: number) => {
        setItems(prev => prev.map(item =>
            item.book.id === bookId ? { ...item, quantity: item.quantity + 1 } : item
        ));
    };

    const handleDecrease = (bookId: number) => {
        setItems(prev => prev.map(item =>
            item.book.id === bookId ? { ...item, quantity: Math.max(item.quantity - 1, 1) } : item
        ));
    };

    const handleRemove = (bookId: number) => {
        setItems(prev => prev.filter(item => item.book.id !== bookId));
    };

    const sendSale = async() => {
        if(!currentUser) { 
            alert("Nenhum usuario logado");
            return;
        }

        try {
            const req: SaleRequest = {
                id: null,
                user: currentUser,
                address: selectedAddressId,
                cards: selectedCards.map(c => ({
                    sale: null,
                    card: c.card,
                    percent: c.percent/100,
                    price: null 
                })),
                books: items.map(i => ({
                    sale: null,
                    book: i.book.id,
                    freight: null,
                    percent: null,
                    quantity: i.quantity
                })),
                freight: null,
                deliveryDate: null,
                status: null,
                traderCoupon: null,
                promotinalCoupons: null
            };

            const res = await api.post<ApiResponse>('/sale', { data: req });
            
            if (res.message) {
                alert(res.message);
                return;
            }

            showToast("Pedido Enviado com Sucesso");


        } catch (err) {
            console.error("Erro ao carregar carrinho", err);
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.content}>
                <div>
                    <SelectAddressMethod onSelect={setSelectedAddressId} />
                    <SelectPaymentMethod purchaseTotal={total} onSelect={setSelectedCards} />
                </div>

                <CartItemsList
                    items={items.map(i => ({
                        id: i.book.id ?? 0,
                        name: i.book.name ?? '',
                        price: i.book.price ?? 0,
                        quantity: i.quantity
                    }))}
                    onIncrease={handleIncrease}
                    onDecrease={handleDecrease}
                    onRemove={handleRemove}
                />
            </div>

            <div className={styles.buttonContent}>
                <Button
                    type='button'
                    text='Finalizar Compra'
                    onClick={() => sendSale()}
                />
            </div>
        </div>
    );

}