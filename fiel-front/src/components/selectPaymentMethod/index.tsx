'use client'
import { CardResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import { useGlobal } from '@/context/GlobalContext';
import { useEffect, useState } from 'react';
import PopUpCard from '../forms/popUpCard';
import styles from './selectPaymentMethod.module.css';
import { CardRequest } from '@/api/dtos/requestDTOs';

interface SelectedCard {
    card: number | null;
    last4: string | null;
    percent: number;
}

export default function SelectPaymentMethod({
    purchaseTotal = 100,
    onSelect
}: {
    purchaseTotal: number;
    onSelect?: (cards: { card: number; percent: number }[]) => void;
}) {
    const [showPopup, setShowPopup] = useState(false);
    const [cards, setCards] = useState<CardResponse[]>([]);
    const [selectedCards, setSelectedCards] = useState<SelectedCard[]>([]);
    const [editedCard, setEditedCard] = useState<CardRequest | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const { currentUser } = useGlobal();

    useEffect(() => {
        if (!currentUser) return;

        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/card/user', { params: { userId: currentUser } });

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data.entities) as CardResponse[] | null;

                if (!entities || entities.length === 0) return;

                setCards(entities);

                const mainCard = entities.find(card => card.principal === true);

                const initial: SelectedCard[] = [
                    {
                        card: mainCard?.id ?? 0,
                        last4: mainCard?.last4 ?? '',
                        percent: 100
                    }
                ];

                setSelectedCards(initial);
            } catch (err) {
                console.error("Erro ao carregar cartões", err);
            }
        }

        fetchData();
    }, [currentUser]);

    useEffect(() => {
        if (onSelect) {
            const validCards = selectedCards
                .filter(sc => sc.card !== null)
                .map(sc => ({ card: sc.card as number, percent: sc.percent }));
            onSelect(validCards);
        }
    }, [selectedCards]);

    const toggleCardSelection = (card: CardResponse) => {
        if (!card.id) return;

        setSelectedCards(prev => {
            const exists = prev.find(sc => sc.card === card.id);

            if (exists) {
                return prev.filter(sc => sc.card !== card.id);
            }

            return [...prev, { card: card.id, last4: card.last4, percent: 0 }];
        });
    };

    const updateCardPercent = (cardId: number | null, percent: number) => {
        if (!cardId) return;
        setSelectedCards(prev =>
            prev.map(sc =>
                sc.card === cardId ? { ...sc, percent } : sc
            )
        );
    };

    const validatePayments = () => {
        const sum = selectedCards.reduce((acc, sc) => acc + sc.percent, 0);
        // Valor mínimo por cartão: pelo menos R$10
        const allAbove10Reais = selectedCards.every(sc => (sc.percent / 100) * purchaseTotal >= 10);
        return sum === 100 && allAbove10Reais;
    };

    const openCreateCardModal = () => {
        const newCard: CardRequest = {
            id: null,
            user: null,
            principal: false,
            bin: '',
            last4: '',
            holder: '',
            expMonth: '',
            expYear: '',
        };

        setEditedCard(newCard);
        setIsModalOpen(true);
    };

    const handleCardChange = (field: keyof CardRequest, value: string | boolean | null) => {
        if (!editedCard) return;

        const newCard: any = { ...editedCard };

        if (field === 'bin' && typeof value === 'string') {
            newCard.pan = value;
        } else if (field === 'expMonth' && typeof value === 'string') {
            newCard.expInput = value;
        } else if (typeof value === 'string' || typeof value === 'boolean' || value === null) {
            newCard[field] = value;
        }

        setEditedCard(newCard);
    };

    const saveCard = async () => {
        if (!editedCard) return;
        if (!confirm('Deseja salvar este cartão?')) return;

        if (!currentUser) {
            alert('Usuário não definido');
            return;
        }

        const pan = (editedCard as any).pan ?? '';
        const expInput = (editedCard as any).expInput ?? '';

        // Parse bin, last4, expMonth, expYear
        const bin = pan.slice(0, 6);
        const last4 = pan.slice(-4);
        const expMonth = expInput.slice(0, 2);
        const expYear = expInput.length === 4 ? `20${expInput.slice(2, 4)}` : '';

        const payload: CardRequest = {
            id: null,
            user: Number(currentUser) as unknown as number,
            principal: editedCard.principal ?? false,
            bin,
            last4,
            holder: editedCard.holder ?? '',
            expMonth,
            expYear
        };

        try {
            const res = await api.post<ApiResponse>('/card', { data: payload });

            if (res.message) {
                alert(res.message);
                return;
            }

            const entity = (res.data.entity ?? null) as CardResponse | null;
            if (!entity) return;

            const updatedCards = cards.some(c => c.id === entity.id)
                ? cards.map(c => (c.id === entity.id ? entity : c))
                : [...cards, entity];

            setCards(updatedCards);
            setIsModalOpen(false);
            setShowPopup(false);
        } catch (err) {
            console.error('Erro ao salvar cartão', err);
            alert('Erro ao salvar cartão.');
        }
    };

    const minPercent = (10 / purchaseTotal) * 100;

    return (
        <div className={styles.container}>
            <div className={styles.paymentCard}>
                <h3>
                    {selectedCards.length > 0
                        ? selectedCards.map(sc =>
                            `Cartão **** ${sc.last4 ?? ''} - ${sc.percent}% (${(
                                (sc.percent / 100) *
                                purchaseTotal
                            ).toLocaleString('pt-BR', {
                                style: 'currency',
                                currency: 'BRL'
                            })})`
                        ).join(', ')
                        : 'Nenhum cartão selecionado'}
                </h3>
                <button
                    onClick={() => setShowPopup(true)}
                    className={styles.changeButton}
                >
                    Selecionar Cartões
                </button>
            </div>

            {showPopup && !isModalOpen && (
                <div className={styles.popupOverlay}>
                    <div className={styles.popup}>
                        <h4>Escolha os cartões e valores</h4>
                        <ul className={styles.paymentList}>
                            {cards.map(card => {
                                const selected = selectedCards.find(
                                    sc => sc.card === card.id
                                );
                                const value =
                                    selected && purchaseTotal
                                        ? (selected.percent / 100) *
                                        purchaseTotal
                                        : 0;
                                return (
                                    <li key={card.id} className={styles.paymentOption}>
                                        <label className={styles.nameCard}>
                                            <input
                                                type="checkbox"
                                                checked={!!selected}
                                                onChange={() =>
                                                    toggleCardSelection(card)
                                                }
                                            />
                                            {`**** ${card.last4}`}
                                        </label>

                                        {selected && (
                                            <div className={styles.valueRow}>
                                                <input
                                                    type="number"
                                                    min={0}
                                                    max={100}
                                                    placeholder="%"
                                                    value={selected.percent}
                                                    onChange={e =>
                                                        updateCardPercent(
                                                            card.id,
                                                            Number(
                                                                e.target.value
                                                            )
                                                        )
                                                    }
                                                    className={styles.amountInput}
                                                />
                                                <span className={styles.valueDisplay}>
                                                    {value.toLocaleString(
                                                        'pt-BR',
                                                        {
                                                            style: 'currency',
                                                            currency: 'BRL'
                                                        }
                                                    )}
                                                </span>
                                            </div>
                                        )}
                                    </li>
                                );
                            })}
                        </ul>

                        <div className={styles.popupActions}>
                            <p>Total da compra: R${purchaseTotal}</p>
                            <p>
                                Soma atual: {selectedCards.reduce(
                                    (acc, sc) => acc + sc.percent,
                                    0
                                )}%
                                {' '}
                                {validatePayments()
                                    ? '✅ válido'
                                    : '❌ inválido'}
                            </p>
                            <p>
                                * Cada cartão deve ter pelo menos R$10, ou seja, no mínimo {(minPercent).toFixed(2)}%
                            </p>
                            <Button
                                type="button"
                                onClick={openCreateCardModal}
                                text="Adicionar Novo Cartão"
                            />
                            <Button
                                type="button"
                                onClick={() => setShowPopup(false)}
                                text="Fechar"
                            />
                        </div>
                    </div>
                </div>
            )}

            {isModalOpen && editedCard && (
                <div className={styles.modalBackdrop}>
                    <div className={styles.modal}>
                        <h3>
                            {editedCard.id ? 'Visualizar Cartão' : 'Criar Cartão'}
                        </h3>

                        <PopUpCard
                            card={editedCard}
                            onChange={handleCardChange}
                            disable={!!editedCard.id}
                        />

                        <div className={styles.modalActions}>
                            <Button type="button" onClick={saveCard} text="Salvar" />
                            <Button
                                type="button"
                                onClick={() => setIsModalOpen(false)}
                                text="Cancelar"
                            />
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
