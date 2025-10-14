'use client'
import { CardResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import { useGlobal } from '@/context/GlobalContext';
import { toRequestCard } from '@/utils/convertDTOs';
import { useEffect, useState } from 'react';
import PopUpCard from '../forms/popUpCard';
import styles from './selectPaymentMethod.module.css';

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
    const [editedCard, setEditedCard] = useState<CardResponse | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const { currentUser } = useGlobal();

    useEffect(() => {
        if(!currentUser) return;

        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/card/user', { params: { userId: currentUser } });
                
                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data.entities) as CardResponse[] | null;

                if(!entities || entities.length === 0) return;

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
                console.error("Erro ao carregar endereços", err);
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
        const allAbove10 = selectedCards.every(sc => sc.percent >= 10);
        return sum === 100 && allAbove10;
    };

    const openCreateCardModal = () => {
        const newCard: CardResponse = {
            id: null,
            user: null,
            principal: false,
            bin: '',
            last4: '',
            holder: '',
            expMonth: '',
            expYear: ''
        };

        setEditedCard(newCard);
        setIsModalOpen(true);
    };

    const saveCard = async () => {
        if (!editedCard) return;
        if (!confirm('Deseja salvar este cartão?')) return;

        try {
            const cardReq = toRequestCard(editedCard);
            const res = await api.post<ApiResponse>('/card', { data: cardReq });

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
                                                    min={10}
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
                                                    {(value).toLocaleString(
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
                                Soma atual:{' '}
                                {selectedCards.reduce(
                                    (acc, sc) => acc + sc.percent,
                                    0
                                )}
                                %{' '}
                                {validatePayments()
                                    ? '✅ válido'
                                    : '❌ inválido'}
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
                        <PopUpCard card={toRequestCard(editedCard)} onChange={saveCard} disable={false} />

                        <div className={styles.modalActions}>
                            <div className={styles.actionButtonsCreate}>
                                <Button
                                    type="button"
                                    onClick={saveCard}
                                    text="Salvar"
                                />
                                <Button
                                    type="button"
                                    onClick={() => setIsModalOpen(false)}
                                    text="Cancelar"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
