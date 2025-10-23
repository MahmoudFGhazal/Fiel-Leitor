'use client';
import { CartResponse } from "@/api/dtos/responseDTOs";
import { ApiResponse } from "@/api/objects";
import api from "@/api/route";
import Button from "@/components/buttonComponents/button";
import QuantityButtons from "@/components/quantityButton";
import { SaleParam } from "@/components/saleComponent";
import { useGlobal } from "@/context/GlobalContext";
import { useEffect, useState } from 'react';
import { FaTrash } from "react-icons/fa";
import styles from './cart.module.css';

type Props = {
    onClose: () => void;
};

export default function CartSidebar({ onClose }: Props) {
    const { currentUser } = useGlobal();
    const [items, setItems] = useState<CartResponse[]>([]);
    const [itemsChanges, setItemsChanges] = useState<CartResponse[]>([]);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/cart', { params: { userId: currentUser } });
                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data) as CartResponse[] | null;

                if (!entities?.length) {
                    setItems([]);
                    return;
                }

                setItems(entities);
                setItemsChanges(entities);
            } catch (err) {
                console.error("Erro ao carregar carrinho", err);
            }
        }

        if (!currentUser) return;
        fetchData();
    }, [currentUser]);
    
    const increaseQuantity = (bookId: number) => {
        setItems(prev =>
            prev.map(item =>
                item.book?.id === bookId
                    ? { ...item, quantity: (item.quantity ?? 0) + 1 }
                    : item
            )
        );
    };

    const decreaseQuantity = (bookId: number) => {
        setItems(prev =>
            prev.map(item =>
                item.book?.id === bookId && (item.quantity ?? 0) > 1
                    ? { ...item, quantity: (item.quantity ?? 0) - 1 }
                    : item
            )
        );
    };

    const removeItem = (bookId: number) => {
        setItems(prev => prev.filter(item => item.book?.id !== bookId));
    };

    const getDifferences = () => {
        const addIds: any[] = [];
        const updateIds: any[] = [];
        const deleteIds: any[] = [];

        const originalMap = new Map(
            itemsChanges.map(item => [item.book?.id, item])
        );

        items.forEach(item => {
            const bookId = item.book?.id;
            if (!bookId) return;

            const original = originalMap.get(bookId);
            if (!original) {
                addIds.push({
                    book: bookId,
                    user: currentUser,
                    quantity: item.quantity ?? 1
                });
            } else if (original.quantity !== item.quantity) {
                updateIds.push({
                    book: bookId,
                    user: currentUser,
                    quantity: item.quantity ?? 1
                });
            }
        });

        itemsChanges.forEach(item => {
            const bookId = item.book?.id;
            if (!bookId) return;

            if (!items.find(i => i.book?.id === bookId)) {
                deleteIds.push({
                    book: bookId,
                    user: currentUser
                });
            }
        });

        const diff = {
            userId: currentUser,
            addIds,
            updateIds,
            deleteIds
        };

        return diff;
    };

    useEffect(() => {
        if (items.length || itemsChanges.length) {
            const diff = getDifferences();
        }
    }, [items]);

    const saveCartChanges = async () => {
        const diff = getDifferences();
        if (!diff) return;

        try {
            const res = await api.post<ApiResponse>('/cart', { data: diff });

            if (res.message) {
                alert(res.message);
            } else {
                setItemsChanges([...items]); 
            }
        } catch (err) {
            console.error("Erro ao atualizar carrinho:", err);
            alert("Erro ao salvar alterações do carrinho.");
        } 
    };

    const handleClose = async () => {
        await saveCartChanges();
        onClose();
    };

    const handlePurchase = async () => {
        await saveCartChanges();

        const saleItems: SaleParam = {
            items: items.map(item => ({
                bookId: item.book?.id ?? 0,
                quantity: item.quantity ?? 1, 
            })),
            fromCart: true
        };

        const queryString = new URLSearchParams({
            items: JSON.stringify(saleItems)
        }).toString();

        window.location.href = `/sale?${queryString}`;
    };

    const handleCleanCart = async () => {
        const res = await api.delete("/cart/clean", { params: { userId: currentUser } }) as ApiResponse;
    
        if(res.message) {
            alert(res.message);
            return;
        }

        setItems([]);
        setItemsChanges([]);
    };

    return (
        <div className={styles.overlay} onClick={handleClose}>
            <div className={styles.cartSidebar} onClick={(e) => e.stopPropagation()}>
                <div className={styles.cartHeader}>
                    <h3>Seu Carrinho</h3>
                    <button onClick={handleClose} className={styles.closeButton}>x</button>
                </div>
                <div className={styles.cartItems}>
                    {items.length === 0 && <p>Seu carrinho está vazio.</p>}

                    {items.map(item => (
                        <div key={item.book?.id ?? Math.random()} className={styles.cartItem}>
                            <div className={styles.nameContent}>
                                <div className={styles.productName} >
                                    <span>{item.book?.name}</span>
                                </div>
                                <div className={styles.trashContent} >
                                    <Button
                                        type='button'
                                        onClick={() => removeItem(item.book?.id ?? 0)}
                                    >
                                        <FaTrash size={15} />
                                    </Button>
                                </div>
                            </div>
                            <QuantityButtons
                                quantity={item.quantity ?? 1}
                                onIncrease={() => increaseQuantity(item.book?.id ?? 0)}
                                onDecrease={() => decreaseQuantity(item.book?.id ?? 0)}
                            />
                        </div>
                    ))}
                </div>

                <div className={styles.purchaseButtonContainer}>
                    <Button
                        type='button'
                        text="Comprar"
                        onClick={handlePurchase}
                        disabled={items.length === 0}
                        dataCy="sale-cart-button"
                    />
                    <Button
                        type='button'
                        text="Limpar"
                        onClick={handleCleanCart}
                        disabled={items.length === 0}
                        dataCy="clear-button"
                    />
                </div>
            </div>
        </div>
    );
}
