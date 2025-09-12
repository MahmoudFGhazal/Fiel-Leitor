/* 'use client';
import { useEffect, useState } from "react";
import { CardData } from "@/modal/cardModal";
import styles from './cardConfig.module.css';
import PopUpCard from "./popUpCard";
import Button from "@/components/button";
import ActionButton from "@/components/actionButton";

export interface UserDataWithCards { 
    id: number; 
    name: string; 
    cards?: CardData[]; 
}

export default function CardConfig() {
    const [user, setUser] = useState<UserDataWithCards | null>(null);
    const [editedCard, setEditedCard] = useState<CardData | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);

    useEffect(() => {
        const localUser = localStorage.getItem('currentUser');
        const sessionUser = sessionStorage.getItem('currentUser');

        const currentUser = localUser
            ? JSON.parse(localUser)
            : sessionUser
            ? JSON.parse(sessionUser)
            : null;

        if (currentUser) setUser(currentUser);
    }, []);

    const openEditModal = (card: CardData) => {
        setEditedCard({ ...card });
        setIsFormEditable(false);
        setIsModalOpen(true);
    };

    const closeModal = () => setEditedCard(null);

    const handleCardChange = (field: keyof CardData, value: string) => {
        if (!editedCard) return;
        setEditedCard(prev => ({ ...prev!, [field]: value }));
    };

    const saveCard = () => {
        if (!user || !editedCard) return;

        const confirmed = confirm("Deseja realmente salvar este cartão?");
        if (!confirmed) return;

        let updatedCards = user.cards || [];
        const exists = updatedCards.some(c => c.id === editedCard.id);

        if (exists) {
            updatedCards = updatedCards.map(c =>
                c.id === editedCard.id ? editedCard : c
            );
        } else {
            updatedCards = [...updatedCards, editedCard];
        }

        const updatedUser = { ...user, cards: updatedCards };
        setUser(updatedUser);

        localStorage.setItem('currentUser', JSON.stringify(updatedUser));
        sessionStorage.setItem('currentUser', JSON.stringify(updatedUser));

        setIsFormEditable(false);
        setIsModalOpen(false);
    };

    const handleDelete = (id: number) => {
        if (!user || !user.cards) return;
        const confirmed = confirm("Tem certeza que deseja apagar este cartão?");
        if (!confirmed) return;

        const updatedCards = user.cards.filter(c => c.id !== id);
        const updatedUser = { ...user, cards: updatedCards };
        setUser(updatedUser);

        localStorage.setItem('currentUser', JSON.stringify(updatedUser));
        sessionStorage.setItem('currentUser', JSON.stringify(updatedUser));
    };
    
    return (
        <div className={styles.container}>
            <div className={styles.headerContent}>
                <h2>Cartões</h2>
                <ActionButton
                    label="Adicionar Cartão"
                    onClick={() => {
                        setEditedCard({
                            id: Date.now(),
                            nickname: '',
                            number: '',
                            holderName: '',
                            expiration: '',
                            cvv: ''
                        });
                        setIsFormEditable(true);
                        setIsModalOpen(true);
                    }}
                />
            </div>

            {user?.cards && user.cards.length > 0 ? (
                <table className={styles.cardTable}>
                    <thead>
                        <tr>
                            <th>Apelido</th>
                            <th>Número</th>
                            <th>Validade</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {user.cards.map(card => (
                            <tr key={card.id}>
                                <td>{card.nickname}</td>
                                <td>**** **** **** {card.number.slice(-4)}</td>
                                <td>{card.expiration}</td>
                                <td>
                                    <div className={styles.actionButtons}>
                                        <button
                                            className={styles.viewButton}
                                            onClick={() => openEditModal(card)}
                                        >
                                            Visualizar/Editar
                                        </button>
                                        <button
                                            className={styles.deleteButton}
                                            onClick={() => handleDelete(card.id)}
                                        >
                                            Apagar
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>Sem cartões cadastrados.</p>
            )}

            {isModalOpen && editedCard && (
                <div className={styles.modalBackdrop}>
                    <div className={styles.modal}>
                        <div className={styles.titleContent}>
                            <h3>{isFormEditable ? 'Editar Cartão' : 'Visualizar Cartão'}</h3>
                        </div>

                        <PopUpCard
                            card={editedCard}
                            onChange={handleCardChange}
                            disable={!isFormEditable}
                        />

                        <div className={styles.modalActions}>
                            {isFormEditable ? (
                                <>
                                    <Button type="button" onClick={saveCard} text="Salvar" />
                                    <Button type="button" onClick={closeModal} text="Cancelar" />
                                </>
                            ) : (
                                <>
                                    <Button type="button" onClick={() => setIsFormEditable(true)} text="Editar" />
                                    <Button type="button" onClick={closeModal} text="Fechar" />
                                </>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
 */