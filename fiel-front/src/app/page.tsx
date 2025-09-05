'use client'
import styles from './page.module.css';
import Field from "@/components/field";
import bookImage from '@/../public/book.png';
import { BookData } from "@/modal/productModal";
import { useEffect, useState } from "react";
import Link from "next/link";

export default function Home() {
    const [fields, setFields] = useState<BookData[]>([]);

    useEffect(() => {
        const storedBooks = localStorage.getItem("books");
        if (storedBooks) {
            try {
                const books: BookData[] = JSON.parse(storedBooks);
                setFields(books);
            } catch (err) {
                console.error("Erro ao ler books do localStorage:", err);
            }
        }
    }, []);

    return (
        <div className={styles.container}>
            {fields.map((f) => (
                <Link key={f.id} href={`/book?bookid=${f.id}`} className={styles.link}>
                    <Field 
                        name={f.name} 
                        price={f.price} 
                        image={bookImage} 
                    />
                </Link>
            ))}
        </div>
    );
}
