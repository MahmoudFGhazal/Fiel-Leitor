'use client'
import { useEffect, useState } from 'react';
import styles from './selectPaymentMethod.module.css';
import { CardData } from '@/modal/cardModal';
import Button from '@/components/button';
import PopUpCard from '../config/cardConfig/popUpCard';

interface SelectedCard {
    card: CardData;
    amount: number;
}

export default function SelectPaymentMethod({ purchaseTotal = 100 }: { purchaseTotal: number }) {
    const [showPopup, setShowPopup] = useState(false);
    const [cards, setCards] = useState<CardData[]>([]);
    const [selectedCards, setSelectedCards] = useState<SelectedCard[]>([]);
    const [editedCard, setEditedCard] = useState<CardData | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);

    // Carrega cartões do usuário
    useEffect(() => {
        const localUser = localStorage.getItem('currentUser');
        const sessionUser = sessionStorage.getItem('currentUser');
        const currentUser = localUser ? JSON.parse(localUser) : sessionUser ? JSON.parse(sessionUser) : null;

        if (currentUser && currentUser.cards) {
            setCards(currentUser.cards);
        }
    }, []);

    const toggleCardSelection = (card: CardData) => {
        setSelectedCards(prev => {
            const exists = prev.find(sc => sc.card.id === card.id);
            if (exists) {
                return prev.filter(sc => sc.card.id !== card.id);
            }
            return [...prev, { card, amount: 0 }];
        });
    };

    const updateCardAmount = (cardId: number, amount: number) => {
        setSelectedCards(prev =>
            prev.map(sc =>
                sc.card.id === cardId ? { ...sc, amount } : sc
            )
        );
    };

    const validatePayments = () => {
        const sum = selectedCards.reduce((acc, sc) => acc + sc.amount, 0);
        const allAbove10 = selectedCards.every(sc => sc.amount >= 10);
        return sum === purchaseTotal && allAbove10;
    };

    const openCreateCardModal = () => {
        const newCard: CardData = {
            id: Date.now(),
            nickname: '',
            number: '',
            holderName: '',
            expiration: '',
            cvv: ''
        };
        setEditedCard(newCard);
        setIsFormEditable(true);
        setIsModalOpen(true);
    };

    const saveCard = () => {
        if (!editedCard) return;

        const confirmed = confirm("Deseja salvar este cartão?");
        if (!confirmed) return;

        const exists = cards.some(c => c.id === editedCard.id);
        let updatedCards;
        if (exists) {
            updatedCards = cards.map(c => c.id === editedCard.id ? editedCard : c);
        } else {
            updatedCards = [...cards, editedCard];
        }

        setCards(updatedCards);

        const localUser = localStorage.getItem('currentUser');
        const sessionUser = sessionStorage.getItem('currentUser');
        const currentUser = localUser ? JSON.parse(localUser) : sessionUser ? JSON.parse(sessionUser) : null;
        if (currentUser) {
            currentUser.cards = updatedCards;
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
        }

        setIsFormEditable(false);
        setIsModalOpen(false);
        setShowPopup(false);
    };

    return (
        <div className={styles.container}>
            <div className={styles.paymentCard}>
                <h3>
                    {selectedCards.length > 0
                        ? selectedCards.map(sc => `${sc.card.nickname} - **** ${sc.card.number.slice(-4)} (R$${sc.amount})`).join(', ')
                        : "Nenhum cartão selecionado"}
                </h3>
                <button onClick={() => setShowPopup(true)} className={styles.changeButton}>
                    Selecionar Cartões
                </button>
            </div>

            {showPopup && !isModalOpen && (
                <div className={styles.popupOverlay}>
                    <div className={styles.popup}>
                        <h4>Escolha os cartões e valores</h4>
                        <ul className={styles.paymentList}>
                            {cards.map(card => {
                                const selected = selectedCards.find(sc => sc.card.id === card.id);
                                return (
                                    <li key={card.id}>
                                        <label className={styles.paymentOption}>
                                            <input
                                                type="checkbox"
                                                checked={!!selected}
                                                onChange={() => toggleCardSelection(card)}
                                            />
                                            {`${card.nickname} - **** **** **** ${card.number.slice(-4)}`}
                                        
                                            {selected && (
                                                <input
                                                    type="number"
                                                    min={10}
                                                    placeholder="Valor"
                                                    value={selected.amount}
                                                    onChange={(e) => updateCardAmount(card.id, Number(e.target.value))}
                                                    className={styles.amountInput}
                                                />
                                            )}
                                        </label>
                                    </li>
                                );
                            })}
                        </ul>
                        <div className={styles.popupActions}>
                            <p>Total da compra: R${purchaseTotal}</p>
                            <p>
                                Soma atual: R${selectedCards.reduce((acc, sc) => acc + sc.amount, 0)}
                                {validatePayments() ? " ✅ válido" : " ❌ inválido"}
                            </p>
                            <Button type="button" onClick={openCreateCardModal} text="Adicionar Novo Cartão" />
                            <Button type="button" onClick={() => setShowPopup(false)} text="Fechar" />
                        </div>
                    </div>
                </div>
            )}

            {isModalOpen && editedCard && (
                <div className={styles.modalBackdrop}>
                    <div className={styles.modal}>
                        <div className={styles.titleContent}>
                            <h3>{isFormEditable ? 'Criar/Editar Cartão' : 'Visualizar Cartão'}</h3>
                        </div>

                        <PopUpCard
                            card={editedCard}
                            onChange={(field, value) => setEditedCard(prev => prev ? { ...prev, [field]: value } : null)}
                            disable={!isFormEditable}
                        />

                        <div className={styles.modalActions}>
                            {isFormEditable ? (
                                <>
                                    <Button type="button" onClick={saveCard} text="Salvar" />
                                    <Button type="button" onClick={() => setIsModalOpen(false)} text="Cancelar" />
                                </>
                            ) : (
                                <>
                                    <Button type="button" onClick={() => setIsFormEditable(true)} text="Editar" />
                                    <Button type="button" onClick={() => setIsModalOpen(false)} text="Fechar" />
                                </>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
