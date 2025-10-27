'use client';
import { AddressRequest } from '@/api/dtos/requestDTOs';
import { AddressResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from "@/api/objects";
import api from '@/api/route';
import ActionButton from "@/components/buttonComponents/actionButton";
import Button from "@/components/buttonComponents/button";
import PopUpAddress from '@/components/forms/popUpAddress';
import { useGlobal } from '@/context/GlobalContext';
import { toRequestAddress } from '@/utils/convertDTOs';
import showToast from '@/utils/showToast';
import { useEffect, useState } from "react";
import styles from './addressConfig.module.css';

export default function AddressConfig() {
    const { currentUser } = useGlobal();
    const [addresses, setAddresses] = useState<AddressResponse[]>([]);
    const [editedAddress, setEditedAddress] = useState<AddressRequest | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/address/user', { params: { userId: currentUser } });

                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data) as AddressResponse[] | null;

                if (!entities?.length) {
                    alert('Nenhum Endereço Encontrado.');
                    return;
                }

                setAddresses(entities);
            } catch (err) {
                console.error("Erro ao carregar dados", err);
            }
        }

        if (!currentUser) return; 

        fetchData();
    }, [currentUser]);

    const openEditModal = (address: AddressResponse, editable = false) => {
        const reqAddress = toRequestAddress(address);
        
        setEditedAddress(reqAddress);
        setIsFormEditable(editable);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setEditedAddress(null);
    };

    const handleAddressChange = (field: keyof AddressRequest, value: string) => {
        if (!editedAddress) return;
        setEditedAddress(prev => ({ ...prev!, [field]: value }));
    };

    const saveAddress = async () => {
        if (!editedAddress) return;
    
        if (!currentUser) {
            alert("Usuário não definido");
            return;
        }

        const payload = {
            ...editedAddress,
            user: currentUser ? Number(currentUser) : null
        };

        let res: ApiResponse;

        if (!payload.id || payload.id === 0) {
            res = await api.post("/address", { data: payload }) as ApiResponse;
        } else {
            res = await api.put("/address", { data: payload }) as ApiResponse;
        }

        if (res.message) {
            alert(res.message);
            return;
        }

        const entity = res.data.entity as AddressResponse;
        
        if (!payload.id) {
            setAddresses(prev => [...prev, entity]);
        } else {
            setAddresses(prev => prev.map(addr => addr.id === entity.id ? entity : addr));
        }

        await showToast(!payload.id ? "Endereço Criado com Sucesso" : "Endereço Atualizado com Sucesso");

        setIsFormEditable(false);
        setIsModalOpen(false);
    };


    const handleDelete = async(id: number) => {
        const confirmed = confirm("Tem certeza que deseja apagar este endereço?");
        if (!confirmed) return;

        const res = await api.put("/address/delete", { params: { addressId: id } }) as ApiResponse;
    
        if(res.message) {
            alert(res.message);
            return;
        }

        const entity = res.data.entity as AddressResponse;

        setAddresses(prev => prev.filter(addr => addr.id !== entity.id));
        await showToast("Endereço removido com sucesso");
    };

    return (
        <div className={styles.container} >
            <div className={styles.headerContent}>
                <h2>Endereços</h2>
                <ActionButton 
                    label="Criar Endereço" 
                    onClick={() => {
                        setEditedAddress({
                            id: null,
                            user: null,
                            principal: null,
                            nickname: '',
                            street: '',
                            number: '',
                            neighborhood: '',
                            city: '',
                            state: '',
                            country: '',
                            zip: '',
                            complement: '',
                            residenceType: null,
                            streetType: null
                        } as AddressRequest);
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
                        <th>Endereço</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {addresses.map((addr) => (
                        <tr key={addr.id}>
                            <td>{addr.nickname}</td>
                            <td>
                                <div className={styles.actionButtons}>            
                                    <ActionButton 
                                        label="Visualizar/Editar" 
                                        onClick={() => openEditModal(addr)} 
                                        color="blue" 
                                        dataCy="view-edit-button"
                                    />

                                    <ActionButton 
                                        label="Apagar" 
                                        onClick={() => handleDelete(addr.id!)} 
                                        color="red" 
                                        dataCy="delete-button"
                                    />
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {isModalOpen && editedAddress && (
                <div className={styles.modalBackdrop}>
                    <div className={styles.modal}>
                        <div className={styles.titleContent}>
                            <h3>{isFormEditable ? 'Editar Endereço' : 'Visualizar Endereço'}</h3>
                        </div>

                        <PopUpAddress
                            address={editedAddress}
                            onChange={handleAddressChange}
                            disable={!isFormEditable}
                        />

                        <div className={styles.modalActions}>
                            {isFormEditable ? (
                                <>
                                    <Button type="button" onClick={saveAddress} text="Salvar" dataCy='save-button'/>
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
