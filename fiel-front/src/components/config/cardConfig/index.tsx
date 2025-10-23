// src/components/cards/CardConfig.tsx
'use client';
import { CardRequest } from '@/api/dtos/requestDTOs';
import { CardResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from "@/api/objects";
import api from '@/api/route';
import ActionButton from "@/components/buttonComponents/actionButton";
import Button from "@/components/buttonComponents/button";
import { useGlobal } from '@/context/GlobalContext';
import showToast from '@/utils/showToast';
import { parseCardData } from '@/utils/validator/CardValidator';
import { useEffect, useState } from "react";
import PopUpCard from '../../forms/popUpCard';
import styles from './cardConfig.module.css';

export default function CardConfig() {
    const { currentUser } = useGlobal();
    const [cards, setCards] = useState<CardResponse[]>([]);
    const [editedCard, setEditedCard] = useState<CardRequest | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/card/user', { params: { userId: currentUser } });
                if (res.message) {
                    alert(res.message);
                    return;
                }
                const data = res.data;
                const entities = (data.entities ?? data) as CardResponse[] | null;

                if (!entities?.length) {
                    setCards([]);
                    return;
                }
                setCards(entities);
            } catch (err) {
                console.error("Erro ao carregar cartões", err);
            }
        }

        if (!currentUser) return;
        fetchData();
    }, [currentUser]);

    const openEditModal = (card?: CardResponse, editable = false) => {
        if (!card) {
            setEditedCard({
                id: null,
                user: null,
                principal: false,
                bin: '',
                last4: '',
                holder: '',
                expMonth: '',
                expYear: '',
                pan: '',
                expInput: ''
            } as unknown as CardRequest);
            setIsFormEditable(true);
            setIsModalOpen(true);
            return;
        }

        const req: CardRequest = {
            id: card.id ?? null,
            user: card?.user?.id ?? null,
            principal: card.principal ?? false,
            bin: card.bin ?? '',
            last4: card.last4 ?? '',
            holder: card.holder ?? '',
            expMonth: card.expMonth ?? '',
            expYear: card.expYear ?? '',
            pan: `${card.bin ?? ''}${card.last4 ?? ''}`, 
            expInput: (card.expMonth && card.expYear) ? `${card.expMonth}${card.expYear.slice(-2)}` : ''
        } as unknown as CardRequest;

        setEditedCard(req);
        setIsFormEditable(editable && !req.id);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setEditedCard(null);
        setIsModalOpen(false);
        setIsFormEditable(false);
    };

    const handleCardChange = (field: keyof CardRequest, value: string | boolean | null) => {
        if (!editedCard) return;

        const newCard: any = { ...editedCard };

        if (field === 'bin' && typeof value === 'string') {
            newCard.pan = value;
        } else if (field === 'expMonth' && typeof value === 'string') {
            newCard.expInput = value;
        } else if (typeof value === 'string' || typeof value === 'boolean' || value === null) {
            (newCard as any)[field] = value;
        }

        setEditedCard(newCard);
    };

    const saveCard = async () => {
        if (!editedCard) return;

        if (editedCard.id) {
            alert("Edição de cartão não é permitida. Exclua e crie um novo.");
            return;
        }

        if (!currentUser) {
            alert("Usuário não definido");
            return;
        }

        const pan = (editedCard as any).pan ?? '';
        const expInput = (editedCard as any).expInput ?? '';

        const parsed = parseCardData(pan, expInput);

        const payload: CardRequest = {
            id: null,
            user: Number(currentUser) as unknown as number,
            principal: editedCard.principal ?? false,
            bin: parsed.bin,
            last4: parsed.last4,
            holder: editedCard.holder ?? '',
            expMonth: parsed.expMonth,
            expYear: parsed.expYear
        } as unknown as CardRequest;

        try {
            const res = await api.post("/card", { data: payload }) as ApiResponse;

            if (res.message) {
                alert(res.message);
                return;
            }

            const entity = res.data.entity as CardResponse;
            setCards(prev => [...prev, entity]);
            await showToast("Cartão criado com sucesso");
            closeModal();
        } catch (err) {
            console.error("Erro ao salvar cartão", err);
            alert("Erro ao salvar cartão");
        }
    };

    const handleDelete = async (id: number) => {
        const confirmed = confirm("Tem certeza que deseja apagar este cartão?");
        if (!confirmed) return;

        const res = await api.delete("/card", { params: { cardId: id } }) as ApiResponse;

        if (res.message) {
            alert(res.message);
            return;
        }

        const entity = res.data.entity as CardResponse;
        setCards(prev => prev.filter(card => card.id !== entity.id));
        await showToast("Cartão removido com sucesso");
    };

    return (
        <div className={styles.container} >
            <div className={styles.headerContent}>
                <h2>Cartões</h2>
                <ActionButton 
                    label="Criar Cartão" 
                    onClick={() => openEditModal(undefined, true)}
                    color='green'
                    dataCy="create-card-button"
                />
            </div>
            <table className={styles.cardTable}>
                <thead>
                    <tr>
                        <th>Cartão</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {cards.map((card) => (
                        <tr key={card.id}>
                            <td>**** **** **** {card.last4}</td>
                            <td>
                                <div className={styles.actionButtons}>            
                                    <ActionButton 
                                        label="Visualizar" 
                                        onClick={() => openEditModal(card, false)} 
                                        color="gray" 
                                        dataCy="view-card-button"
                                    />

                                    <ActionButton 
                                        label="Apagar" 
                                        onClick={() => handleDelete(card.id!)} 
                                        color="red" 
                                        dataCy="delete-card-button"
                                    />
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {isModalOpen && editedCard && (
                <div className={styles.modalBackdrop} data-cy="card-modal-backdrop">
                    <div className={styles.modal} data-cy="card-modal">
                        <div className={styles.titleContent}>
                            <h3>{editedCard.id ? 'Visualizar Cartão' : (isFormEditable ? 'Criar Cartão' : 'Visualizar Cartão')}</h3>
                        </div>

                        <PopUpCard
                            card={editedCard}
                            onChange={handleCardChange}
                            disable={!!editedCard.id || !isFormEditable}
                        />

                        <div className={styles.modalActions}>
                            {editedCard.id ? (
                                <>
                                    <Button type="button" onClick={closeModal} text="Fechar" dataCy="close-card-button" />
                                </>
                            ) : (
                                <>
                                    <Button type="button" onClick={saveCard} text="Salvar" dataCy="save-card-button" />
                                    <Button type="button" onClick={closeModal} text="Cancelar" dataCy="cancel-card-button" />
                                </>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
