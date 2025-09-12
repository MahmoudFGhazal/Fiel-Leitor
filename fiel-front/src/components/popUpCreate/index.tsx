'use client'
import { useState } from 'react';
import { BookData, Categories } from '@/modal/productModal';
import styles from './createBookPopup.module.css';
import Input from '@/components/input';
import { CategoriesPortuguese } from '@/modal/translate/portuguses';
import Button from '@/components/buttonComponents/button';

interface Props {
    onClose: () => void;
    onCreate: (book: BookData) => void;
}

export default function CreateBookPopup({ onClose, onCreate }: Props) {
    const [form, setForm] = useState<BookData>({
        id: 0,
        name: '',
        categories: [],
        price: 0,
        author: '',
        description: '',
        stock: 0,
        isActive: true
    });

    const handleChange = (name: string, value: unknown) => {
        if (name === 'categories') {
            setForm({ ...form, categories: value as Categories[] });
        } else if (name === 'price' || name === 'stock') {
            setForm({ ...form, [name]: Number(value) });
        } else {
            setForm({ ...form, [name]: value as string });
        }
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        
        const newBook = {
            ...form,
            id: Date.now()
        }
        
        onCreate(newBook);
        onClose();
    };

    return (
        <div className={styles.overlay}>
            <div className={styles.popup}>
                <h3>Criar Novo Livro</h3>
                <form onSubmit={handleSubmit} className={styles.form}>
                    <Input
                        type='text'
                        text="Nome"
                        value={form.name}
                        onChange={(val) => handleChange('name', val)}
                    />
                    <Input
                        text="Categorias"
                        value={form.categories} 
                        onChange={(val) => handleChange('categories', val)}
                        options={Object.values(CategoriesPortuguese).map((cat) => ({
                            value: cat,
                            label: cat,
                        }))}
                        multiple
                    />
                    <Input
                        type='number'
                        text="Preço"
                        value={String(form.price)}
                        onChange={(val) => handleChange('price', val)}
                    />
                    <Input
                        type='number'
                        text="Estoque"
                        value={String(form.stock)}
                        onChange={(val) => handleChange('stock', val)}
                    />
                    <Input
                        type='text'
                        text="Descrição"
                        value={form.description}
                        onChange={(val) => handleChange('description', val)}
                    />
                    <div className={styles.actions}>
                        <Button
                            type='button'
                            text="Cancelar"
                            onClick={onClose}
                        />
                        <Button
                            type='submit'
                            text="Salvar"
                        />
                    </div>
                </form>
            </div>
        </div>
    );
}
