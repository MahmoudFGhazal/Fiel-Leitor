'use client'
import { AddressRequest } from '@/api/dtos/requestDTOs';
import { AddressResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Button from '@/components/buttonComponents/button';
import { useGlobal } from '@/context/GlobalContext';
import { useEffect, useState } from 'react';
import PopUpAddress from '../forms/popUpAddress';
import styles from './selectAddressMethod.module.css';

export default function SelectAddressMethod({ onSelect }: { onSelect?: (addressId: number) => void }) {
    const [showPopup, setShowPopup] = useState(false);
    const [selectedAddress, setSelectedAddress] = useState<AddressResponse | null>(null);
    const [addresses, setAddresses] = useState<AddressResponse[]>([]);
    const [editedAddress, setEditedAddress] = useState<AddressRequest  | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);
    const { currentUser } = useGlobal();

    useEffect(() => {
        if(!currentUser) return;

        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/address/user', { params: { userId: currentUser } });
                
                if (res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;
                const entities = (data.entities ?? data.entities) as AddressResponse[] | null;

                if(!entities || entities.length === 0) return;

                setAddresses(entities);

                const mainAddress = entities.find(addr => addr.principal === true);

                setSelectedAddress(mainAddress ?? entities[0]);
                if (mainAddress?.id && onSelect) onSelect(mainAddress.id);
            } catch (err) {
                console.error("Erro ao carregar endereços", err);
            }
        }
        
        fetchData();
    }, [currentUser]);

    const handleSelectAddress = (address: AddressResponse) => {
        setSelectedAddress(address);
        setShowPopup(false);
        if (onSelect && address.id) onSelect(address.id);
    };

    const openCreateAddressModal = () => {
        const newAddress: AddressRequest = {
            id: Date.now(),
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
            streetType: null,
            user: null,
            principal: null
        };

        setEditedAddress(newAddress);
        setIsFormEditable(true);
        setIsModalOpen(true);
    };

    const saveAddress = async () => {
        if (!editedAddress) return;

        if (!confirm("Deseja salvar este endereço?")) return;

        try {
            const res = await api.post<ApiResponse>('/address', { data: editedAddress });
            
            if (res.message) {
                alert(res.message);
                return;
            }

            const dataCreate = res.data;
            const entityCreate = (dataCreate.entity ?? dataCreate.entity) as AddressResponse | null;

            if(!entityCreate) return;

            const updatedAddresses = addresses.some(addr => addr.id === entityCreate.id)
                ? addresses.map(addr => (addr.id === entityCreate.id ? entityCreate : addr))
                : [...addresses, entityCreate];

            setAddresses(updatedAddresses);
            setSelectedAddress(entityCreate);

            setIsModalOpen(false);
            setIsFormEditable(false);
            setShowPopup(false);
        } catch (err) {
            console.error("Erro ao salvar endereço", err);
        }
    };

    return (
        <div className={styles.container}>
            <div className={styles.addressCard}>
                <h3>
                    {selectedAddress
                        ? selectedAddress.nickname
                        : 'Nenhum endereço selecionado'
                    }
                </h3>
                <button onClick={() => setShowPopup(true)} className={styles.changeButton}>
                    Escolher outro
                </button>
            </div>

            {showPopup && !isModalOpen && (
                <div className={styles.popupOverlay}>
                    <div className={styles.popup}>
                        <h4>Escolha um endereço</h4>
                        <ul className={styles.addressList}>
                            {addresses.map(address => (
                                <li key={address.id}>
                                    <button
                                        onClick={() => handleSelectAddress(address)}
                                        className={styles.addressOption}
                                    >
                                        {address.nickname}
                                    </button>
                                </li>
                            ))}
                        </ul>
                        <div className={styles.popupActions}>
                            <Button type="button" onClick={openCreateAddressModal} text="Criar Novo Endereço" />
                            <Button type="button" onClick={() => setShowPopup(false)} text="Fechar" />
                        </div>
                    </div>
                </div>
            )}

            {isModalOpen && editedAddress && (
                <div className={styles.modalBackdrop}>
                    <div className={styles.modal}>
                        <div className={styles.titleContent}>
                            <h3>{isFormEditable ? 'Criar/Editar Endereço' : 'Visualizar Endereço'}</h3>
                        </div>

                        <PopUpAddress
                            address={editedAddress}
                            onChange={(field, value) =>
                                setEditedAddress(prev => prev ? { ...prev, [field]: value } : null)
                            }
                            disable={!isFormEditable}
                        />

                        <div className={styles.modalActions}>
                            {isFormEditable ? (
                                <div className={styles.actionButtonsCreate}>
                                    <Button type="button" onClick={saveAddress} text="Salvar" />
                                    <Button type="button" onClick={() => setIsModalOpen(false)} text="Cancelar" />
                                </div>
                            ) : (
                                <div className={styles.actionButtonsCreate}>
                                    <Button type="button" onClick={() => setIsFormEditable(true)} text="Editar" />
                                    <Button type="button" onClick={() => setIsModalOpen(false)} text="Fechar" />
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
