/* 'use client';
import { useEffect, useState } from "react";
import { UserData } from "@/modal/userModal";
import styles from './addressConfig.module.css';
import { AddressData } from "@/modal/addressModal";
import PopUpAddress from "./popUpAddress";
import Button from "@/components/button";
import ActionButton from "@/components/actionButton";

export default function AddressConfig() {
    const [user, setUser] = useState<UserData | null>(null);

    const [editedAddress, setEditedAddress] = useState<AddressData | null>(null);

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

        if (currentUser) {
            setUser(currentUser);
        }
    }, []);

    const openEditModal = (address: AddressData) => {
        setEditedAddress({ ...address });
        setIsFormEditable(false);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setEditedAddress(null);
    };

    const handleAddressChange = (field: keyof AddressData, value: string) => {
        if (!editedAddress) return;
        setEditedAddress(prev => ({ ...prev!, [field]: value }));
    };

    const saveAddress = () => {
        if (!user || !editedAddress) return;

        const confirmed = confirm("Deseja realmente salvar este endereço?");
        if (!confirmed) return;

        let updatedAddresses;

        const exists = user.addresses.some(addr => addr.id === editedAddress.id);

        if (exists) {
            updatedAddresses = user.addresses.map(addr =>
                addr.id === editedAddress.id ? editedAddress : addr
            );
        } else {
            updatedAddresses = [...user.addresses, editedAddress];
        }

        const updatedUser = { ...user, addresses: updatedAddresses };
        setUser(updatedUser);

        localStorage.setItem('currentUser', JSON.stringify(updatedUser));
        sessionStorage.setItem('currentUser', JSON.stringify(updatedUser));

        setIsFormEditable(false);
        setIsModalOpen(false); 
    };


    const handleDelete = (id: number) => {
        if (!user) return;
        const confirmed = confirm("Tem certeza que deseja apagar este endereço?");
        if (!confirmed) return;

        const updatedAddresses = user.addresses.filter(addr => addr.id !== id);
        const updatedUser = { ...user, addresses: updatedAddresses };
        setUser(updatedUser);

        localStorage.setItem('user', JSON.stringify(updatedUser));
    };

    if (!user) {
        return <p>Sem usuário logado.</p>;
    }

    if (!user.addresses || user.addresses.length === 0) {
        return <p>Sem endereços cadastrados.</p>;
    }

    return (
        <div className={styles.container} >
            <div className={styles.headerContent}>
                <h2>Endereços</h2>
                <ActionButton 
                    label="Criar Endereço" 
                    onClick={() => {
                        setEditedAddress({
                            id: Date.now(),
                            street: '',
                            number: '',
                            neighborhood: '',
                            city: '',
                            state: '',
                            country: '',
                            zip: '',
                            complement: '',
                            typeResidence: null,
                            typeStreet: null
                        });
                        setIsFormEditable(true);   
                        setIsModalOpen(true);
                    }} 
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
                    {user.addresses.map((addr) => (
                        <tr key={addr.id}>
                            <td>
                                {addr.street}, {addr.number} - {addr.neighborhood}, {addr.city}/{addr.state} - {addr.country}
                            </td>
                            <td>
                                <div className={styles.actionButtons}>
                                    <button 
                                        className={styles.viewButton} 
                                        onClick={() => openEditModal(addr)}
                                    >
                                        Visualizar/Editar
                                    </button>
                                    <button 
                                        className={styles.deleteButton} 
                                        onClick={() => handleDelete(addr.id)}
                                    >
                                        Apagar
                                    </button>
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
                                    <Button type="button" onClick={saveAddress} text="Salvar" />
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