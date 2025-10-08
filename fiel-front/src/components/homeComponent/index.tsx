'use client'
import { BookResponse } from '@/api/dtos/responseDTOs';
import { ApiResponse } from '@/api/objects';
import api from '@/api/route';
import Link from 'next/link';
import { useEffect, useState } from 'react';
import bookImage from '../../../public/book.png';
import Field from '../field';
import styles from './homeComponent.module.css';

export default function HomeComponent() {
    const [fields, setFields] = useState<BookResponse[]>([]);

    useEffect(() => {
        async function fetchData() {
            try {
                const res = await api.get<ApiResponse>('/book');
                
                if (res.message) {
                    alert(res.message);
                    return;
                }
                const data = res.data;
                const entities = (data.entities ?? data.entities) as BookResponse[] | null;

                if (!entities?.length) {
                    setFields([]);
                    return;
                }
                setFields(entities);
            } catch (err) {
                console.error("Erro ao carregar cartões", err);
            }
        }

        fetchData();
    }, []);
    
    return (
        <div className={styles.container}>
            {fields.map((f) => (
                <Link key={f.id} href={`/book?bookid=${f.id}`} className={styles.link}>
                    <Field 
                        name={f.name ?? 'Sem título'}
                        price={f.price ?? 0}
                        image={bookImage} 
                    />
                </Link>
            ))}
        </div>
    );
}