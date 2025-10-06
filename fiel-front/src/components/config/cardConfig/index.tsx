'use client';
import { CardRequest } from '@/api/dtos/requestDTOs';
import { CardResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from "@/api/objects";
import api from '@/api/route';
import ActionButton from "@/components/buttonComponents/actionButton";
import Button from "@/components/buttonComponents/button";
import { useGlobal } from '@/context/GlobalContext';
import showToast from '@/utils/showToast';
import { toRequestCard } from '@/utils/toRequest';
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
                    alert('Nenhum cartão encontrado.');
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

    const openEditModal = (card: CardResponse, editable = false) => {
        const reqCard = toRequestCard(card);
        
        setEditedCard(reqCard);
        setIsFormEditable(editable);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setEditedCard(null);
        setIsModalOpen(false);
    };

    const handleCardChange = (field: keyof CardRequest, value: string | boolean | null) => {
        if (!editedCard) return;
        setEditedCard(prev => ({ ...prev!, [field]: value }));
    };

    const saveCard = async () => {
        if (!editedCard) return;
    
        if (!currentUser) {
            alert("Usuário não definido");
            return;
        }

        const payload = {
            ...editedCard,
            user: Number(currentUser)
        };

        let res: ApiResponse;

        if (!payload.id || payload.id === 0) {
            res = await api.post("/card", { data: payload }) as ApiResponse;
        } else {
            res = await api.put("/card", { data: payload }) as ApiResponse;
        }

        if (res.message) {
            alert(res.message);
            return;
        }

        const entity = res.data.entity as CardResponse;
        
        if (!payload.id) {
            setCards(prev => [...prev, entity]);
        } else {
            setCards(prev => prev.map(card => card.id === entity.id ? entity : card));
        }

        await showToast(!payload.id ? "Cartão criado com sucesso" : "Cartão atualizado com sucesso");

        setIsFormEditable(false);
        setIsModalOpen(false);
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
                    onClick={() => {
                        setEditedCard({
                            id: null,
                            user: null,
                            principal: false,
                            bin: '',
                            last4: '',
                            holder: '',
                            expMonth: '',
                            expYear: ''
                        } as CardRequest);
                        setIsFormEditable(true);   
                        setIsModalOpen(true);
                    }} 
                    color='green'
                    dataCy='create-button'
                />
            </div>
            <table className={styles.addressTable}>
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
                                        dataCy="view-button"
                                    />

                                    <ActionButton 
                                        label="Editar" 
                                        onClick={() => openEditModal(card, true)} 
                                        color="blue" 
                                        dataCy="edit-button"
                                    />

                                    <ActionButton 
                                        label="Apagar" 
                                        onClick={() => handleDelete(card.id!)} 
                                        color="red" 
                                        dataCy="delete-button"
                                    />
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

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
                                    <Button type="button" onClick={saveCard} text="Salvar" dataCy='save-button'/>
                                    <Button type="button" onClick={closeModal} text="Cancelar" dataCy='cancel-button' />
                                </>
                            ) : (
                                <>
                                    <Button type="button" onClick={() => setIsFormEditable(true)} text="Editar" dataCy='edit-button' />
                                    <Button type="button" onClick={closeModal} text="Fechar" dataCy='close-button' />
                                </>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
