'use client';
import api from '@/api/route';
import styles from './addressConfig.module.css';
import { Address, ApiResponse, User } from "@/api/objects";
import ActionButton from "@/components/buttonComponents/actionButton";
import Button from "@/components/buttonComponents/button";
import { useEffect, useState } from "react";
import { useGlobal } from '@/context/GlobalContext';
import PopUpAddress from '@/components/forms/popUpAddress';
import showToast from '@/utils/showToast';

export default function AddressConfig() {
    const { currentUser } = useGlobal();
    const [addresses, setAddresses] = useState<Address[]>([]);
    const [editedAddress, setEditedAddress] = useState<Address | null>();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isFormEditable, setIsFormEditable] = useState(false);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/address/user', { params: { userId: currentUser } });

                if(res.message) {
                    alert(res.message);
                    return;
                }

                const data = res.data;

                const entities = (data.entities ?? data) as Address[] | null;

                if (!entities?.length) {
                    alert('Nenhum Endereço Encontrado.');
                    return;
                }

                setAddresses(entities);
            } catch (err) {
                console.error("Erro ao carregar dados", err);
            }
        }

        fetchData();
    }, []);

    const openEditModal = (address: Address, editable = false) => {
        setEditedAddress({ ...address });
        setIsFormEditable(editable);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setEditedAddress(null);
    };

    const handleAddressChange = (field: keyof Address, value: string) => {
        if (!editedAddress) return;
        setEditedAddress(prev => ({ ...prev!, [field]: value }));
    };

    const saveAddress = async () => {
        if (!editedAddress) return;
        console.log(JSON.stringify(editedAddress, null, 2))
        let res: ApiResponse;

        if (!editedAddress.id || editedAddress.id === 0) {
            console.log("Criar")
            res = await api.post("/address", { data: editedAddress }) as ApiResponse;
        
            if (res.message) {
                alert(res.message);
                return;
            }

            await showToast("Endereço Criado com Sucesso");
        } else {
            console.log("Atualizar")
            res = await api.put(`/address`, { data: editedAddress }) as ApiResponse;
        
            if (res.message) {
                alert(res.message);
                return;
            }

            await showToast("Endereço Atualizado com Sucesso");
        }

        setIsFormEditable(false);
        setIsModalOpen(false);
    };


    const handleDelete = (id: number) => {
        const confirmed = confirm("Tem certeza que deseja apagar este endereço?");
        if (!confirmed) return;

        const updatedAddresses = addresses.filter(addr => addr.id !== id);
    };

    if (!addresses || addresses.length === 0) {
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
                            id: null,
                            user: { id: currentUser } as User,
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
                    {addresses.map((addr) => (
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
                                        onClick={() => handleDelete(addr.id!)}
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
