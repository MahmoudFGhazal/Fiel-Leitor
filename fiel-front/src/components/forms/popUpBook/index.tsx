'use client'
import { BookResponse } from '@/api/dtos/responseDTOs';
import Button from '@/components/buttonComponents/button';
import Input from '@/components/input';
import { useState } from 'react';
import styles from './createBookPopup.module.css';

interface Props {
    onClose: () => void;
    onCreate: (book: BookResponse) => void;
}

export default function PopUpBookCreate({ onClose, onCreate }: Props) {
    const [form, setForm] = useState<BookResponse>({
        id: null,
        name: '',
        category: null,
        price: 0,
        stock: 0,
        active: false
    });

    const handleChange = (name: string, value: unknown) => {
        setForm((prev) => {
            switch (name) {
                case 'name':
                case 'description':
                return { ...prev, [name]: String(value ?? '') };

                case 'price': {
                    const num = Number(value);
                    return { ...prev, price: Number.isFinite(num) ? num : 0 };
                }

                case 'stock': {
                    const num = Number(value);
                    return { ...prev, stock: Number.isFinite(num) ? Math.trunc(num) : 0 };
                }

                case 'categories': {
                    if (Array.isArray(value)) {
                        return { ...prev, categories: value.map(String) };
                    }
                    if (typeof value === 'string') {
                        return { ...prev, categories: [value] };
                    }
                    return prev;
                }

                case 'active': {
                    return { ...prev, active: Boolean(value) };
                }

                default:
                return prev;
            }
        });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

       /*  if (!form.name.trim()) {
        alert('Informe o nome do livro.');
        return;
        }
        if (form.price < 0) {
        alert('Preço não pode ser negativo.');
        return;
        }
        if (form.stock < 0) {
        alert('Estoque não pode ser negativo.');
        return;
        }

        const newBook: BookResponse = {
            id: Date.now(),
            name: form.name,
            category: form.category ?? null,
            price: form.price,
            stock: form.stock,
            description: form.description,
            active: form.active,
        } as unknown as BookResponse;
        
        onCreate(newBook); */
        onClose();
    };

    return (
        <div className={styles.overlay}>
            <div className={styles.popup}>
                <h3>Criar Novo Livro</h3>
                <form onSubmit={handleSubmit} className={styles.form}>
                    {/* <Input
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
                    /> */}
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
                    {/* <Input
                        type='text'
                        text="Descrição"
                        value={form.description}
                        onChange={(val) => handleChange('description', val)}
                    /> */}
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
