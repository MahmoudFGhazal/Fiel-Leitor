'use client'
import { useState } from 'react';
import styles from './page.module.css';
import ProfileConfig from '@/components/config/profileConfig';
import Button from '@/components/button';
import AddressConfig from '@/components/config/addressConfig';
import CardConfig from '@/components/config/cardConfig';

type Tab = 'profile' | 'addresses' | 'cards';

export default function ConfigComponent() {
    const [activeTab, setActiveTab] = useState<Tab>('profile');

    return (
        <div className={styles.container}>
            <div className={styles.sideBar}>
                <Button
                    type="button"
                    text="Profile"
                    onClick={() => setActiveTab('profile')}
                />
                <Button
                    type="button"
                    text="Endereços"
                    onClick={() => setActiveTab('addresses')}
                />
                <Button
                    type="button"
                    text="Cartões"
                    onClick={() => setActiveTab('cards')}
                />
            </div>

            <div className={styles.tabContent}>
                {activeTab === 'profile' && <ProfileConfig />}
                {activeTab === 'addresses' && <AddressConfig />}
                {activeTab === 'cards' && <CardConfig />}
            </div>
        </div>
    );
}