'use server'
import CRUDClientComponent from '@/components/crud/client';
import styles from './page.module.css';

export default async function CRUDClient() {

    return (
        <CRUDClientComponent />
    );
}