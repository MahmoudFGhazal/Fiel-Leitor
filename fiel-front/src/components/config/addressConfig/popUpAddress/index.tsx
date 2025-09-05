'use client';
import { AddressData } from "@/modal/addressModal";
import styles from './popUpAddress.module.css';
import Input from "@/components/input";
import { TypeResidencesPortuguese, TypeStreetsPortuguese } from "@/modal/translate/portuguses";

interface Props {
    address: AddressData;
    onChange: (field: keyof AddressData, value: string) => void;
    disable: boolean;
}

export default function PopUpAddressCreate({ address, onChange, disable }: Props) {
    return (
        <div className={styles.formContainer}>
            <div className={styles.inputsContent}>
                <Input
                    type="text"
                    text="Rua"
                    value={address.street ?? ""}
                    onChange={(value) => onChange('street', value)}
                    disabled={disable}
                />
                <Input
                    type="text"
                    text="Número"
                    value={address.number ?? ""}
                    onChange={(value) => onChange('number', value)}
                    disabled={disable}
                />
            </div>
            <div className={styles.inputsContent}>
                <Input
                    type="text"
                    text="Bairro"
                    value={address.neighborhood ?? ""}
                    onChange={(value) => onChange('neighborhood', value)}
                    disabled={disable}
                />
                <Input
                    type="text"
                    text="Cidade"
                    value={address.city}
                    onChange={(value) => onChange('city', value)}
                    disabled={disable}
                />
            </div>
            <div className={styles.inputsContent}>
                <Input
                    text="Tipo de Residência"
                    value={address.typeResidence || ""}
                    onChange={(val) => onChange('typeResidence', val)}
                    options={Object.entries(TypeResidencesPortuguese).map(([key, label]) => ({
                        value: key,
                        label,
                    }))}
                    disabled={disable}
                />

                <Input
                    text="Tipo de Logradouro"
                    value={address.typeStreet || ""}
                    onChange={(val) => onChange('typeStreet', val)}
                    options={Object.entries(TypeStreetsPortuguese).map(([key, label]) => ({
                        value: key,
                        label,
                    }))}
                    disabled={disable}
                />
            </div>
            <div className={styles.inputsContent}>
                <Input
                    type="text"
                    text="Estado"
                    value={address.state ?? ""}
                    onChange={(value) => onChange('state', value)}
                    disabled={disable}
                />
                <Input
                    type="text"
                    text="País"
                    value={address.country ?? ""}
                    onChange={(value) => onChange('country', value)}
                    disabled={disable}
                />
            </div>
            <div className={styles.inputsContent}>
                <Input
                    type="text"
                    text="CEP"
                    value={address.zip ?? ""}
                    onChange={(value) => onChange('zip', value)}
                    disabled={disable}
                />
                <Input
                    type="text"
                    text="Complemento"
                    value={address.complement ?? ""}
                    onChange={(value) => onChange('complement', value)}
                    disabled={disable}
                />
            </div>
        </div>
    );
}
