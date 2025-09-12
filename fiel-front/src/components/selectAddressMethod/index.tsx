'use client'
import { useEffect, useState } from 'react';
import styles from './selectAddressMethod.module.css';
import { AddressData } from '@/modal/addressModal';
import Button from '@/components/buttonComponents/button';
import PopUpAddress from '../config/addressConfig/popUpAddress';

export default function SelectAddressMethod() {
    const [showPopup, setShowPopup] = useState(false);
    const [selectedAddress, setSelectedAddress] = useState<AddressData | null>(null);
    const [addresses, setAddresses] = useState<AddressData[]>([]);
    const [editedAddress, setEditedAddress] = useState<AddressData | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);

    // Carrega endereços do usuário
    useEffect(() => {
        const localUser = localStorage.getItem('currentUser');
        const sessionUser = sessionStorage.getItem('currentUser');

        const currentUser = localUser
            ? JSON.parse(localUser)
            : sessionUser
            ? JSON.parse(sessionUser)
            : null;

        if (currentUser && currentUser.addresses) {
            setAddresses(currentUser.addresses);
            if (currentUser.addresses.length > 0) setSelectedAddress(currentUser.addresses[0]);
        }
    }, []);

    const handleSelectAddress = (address: AddressData) => {
        setSelectedAddress(address);
        setShowPopup(false);
    };

    const openCreateAddressModal = () => {
        const newAddress: AddressData = {
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
        };
        setEditedAddress(newAddress);
        setIsFormEditable(true);
        setIsModalOpen(true);
    };

    const saveAddress = () => {
        if (!editedAddress) return;

        const confirmed = confirm("Deseja salvar este endereço?");
        if (!confirmed) return;

        const exists = addresses.some(addr => addr.id === editedAddress.id);
        let updatedAddresses;
        if (exists) {
            updatedAddresses = addresses.map(addr =>
                addr.id === editedAddress.id ? editedAddress : addr
            );
        } else {
            updatedAddresses = [...addresses, editedAddress];
        }

        setAddresses(updatedAddresses);

        const localUser = localStorage.getItem('currentUser');
        const sessionUser = sessionStorage.getItem('currentUser');
        const currentUser = localUser ? JSON.parse(localUser) : sessionUser ? JSON.parse(sessionUser) : null;
        if (currentUser) {
            currentUser.addresses = updatedAddresses;
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            sessionStorage.setItem('currentUser', JSON.stringify(currentUser));
        }

        setIsFormEditable(false);
        setIsModalOpen(false);
        setSelectedAddress(editedAddress);
        setShowPopup(false);
    };

    return (
        <div className={styles.container}>
            <div className={styles.addressCard}>
                <h3>
                    {selectedAddress
                        ? `${selectedAddress.street}, ${selectedAddress.number} - ${selectedAddress.neighborhood}, ${selectedAddress.city}/${selectedAddress.state} - ${selectedAddress.country}`
                        : "Nenhum endereço selecionado"}
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
                                        {`${address.street}, ${address.number} - ${address.neighborhood}, ${address.city}/${address.state} - ${address.country}`}
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
                            onChange={(field, value) => setEditedAddress(prev => prev ? { ...prev, [field]: value } : null)}
                            disable={!isFormEditable}
                        />

                        <div className={styles.modalActions}>
                            {isFormEditable ? (
                                <>
                                    <Button type="button" onClick={saveAddress} text="Salvar" />
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
