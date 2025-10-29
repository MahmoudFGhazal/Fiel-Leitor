'use client'
import { BookRequest } from '@/api/dtos/requestDTOs';
import { BookResponse, CategoryResponse } from '@/api/dtos/responseDTOs';
import Button from '@/components/buttonComponents/button';
import InputSelect from '@/components/inputComponents/inputSelect';
import InputText from '@/components/inputComponents/inputText';
import { CategoriesPortuguese } from '@/translate/portuguses';
import { toRequestBook } from '@/utils/convertDTOs';
import { getCategories } from '@/utils/getTypes';
import { useEffect, useState } from 'react';
import styles from './createBookPopup.module.css';

interface Props {
    onClose: () => void;
    onSave: (book: BookRequest) => void;
    book?: BookResponse;
}

export default function PopUpBookCreate({ onClose, onSave, book }: Props) {
    const [categoryTypes, setCategoryTypes] = useState<CategoryResponse[] | null>(null);
        
    const [form, setForm] = useState<BookRequest>({
        id: null,
        name: '',
        category: null,
        price: 0,
        stock: 0,
        active: true
    });

    useEffect(() => {
        async function fetchData() {
            try {
                const categories: CategoryResponse[] = await getCategories();

                setCategoryTypes(categories);
            } catch (err) {
                console.error("Erro ao carregar dados", err);
            }
        }

        if(book) {
            const bookReq = toRequestBook(book);

            setForm(bookReq);
        }

        fetchData();
    }, []);

    const updateForm = (data: Partial<BookRequest>) =>
        setForm(prev => {
            const next = { ...prev };

            if ('name' in data) {
                next.name = String(data.name ?? '').trim();
            }

            if ('price' in data) {
                const raw = (data.price as any)?.toString?.() ?? '';
                const parsed = Number(raw.replace(',', '.'));
                const nonNeg = Number.isFinite(parsed) && parsed >= 0 ? parsed : 0;
                next.price = Math.round(nonNeg * 100) / 100;
            }

            if ('stock' in data) {
                const n = Number((data.stock as any));
                next.stock = Number.isFinite(n) ? Math.max(0, Math.trunc(n)) : 0;
            }

            if ('category' in data) {
                const raw = (data.category as any);
                if (raw === null || raw === '') {
                    next.category = null;
                } else {
                    const n = typeof raw === 'number' ? raw : Number(raw);
                    next.category = Number.isFinite(n) ? n : prev.category;
                }
            }

            return next;
        }
    );

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onSave(form);
    };

    return (
        <div className={styles.overlay}>
            <div className={styles.popup}>
                <h3>Criar Novo Livro</h3>
                <form data-cy="book-form" onSubmit={handleSubmit} className={styles.form}>
                    <InputText
                        type='text'
                        text="Nome"
                        onChange={(val) => updateForm({ name: val as string })}
                        value={String(form.name)}    
                        dataCy='name-text'              
                    />
                    <InputSelect
                        text="Categoria"
                        value={form.category != null ? String(form.category) : ''}
                        onChange={(val: string) => {
                            const n = val ? Number(val) : null;
                            updateForm({ category: Number.isFinite(n as number) ? (n as number) : null });
                        }}
                        options={
                            (categoryTypes ?? []).map(c => ({
                                value: c.id?.toString() || '',
                                label: CategoriesPortuguese[
                                    c.category as keyof typeof CategoriesPortuguese
                                ] ?? c.category
                            }))
                        }
                        dataCy="category-select"
                    />
                    <InputText
                        type='number'
                        text="Preço"
                        value={String(form.price)}
                        onChange={(val) => updateForm({ price: Number(val) })}
                    />
                    <InputText
                        type='number'
                        text="Estoque"
                        value={String(form.stock)}
                        onChange={(val) => updateForm({ stock: Number(val) })}
                        dataCy='stock-text'
                    />
                    {/* <InputText
                        type='text'
                        text="Descrição"
                        onChange={(val) => updateForm({ description: val as string })}                   
                    /> */}
                    <div className={styles.actions}>
                        <Button
                            type='button'
                            text="Cancelar"
                            onClick={onClose}
                            dataCy='cancel-button'
                        />
                        <Button
                            type='submit'
                            text="Salvar"
                            dataCy='save-button'
                        />
                    </div>
                </form>
            </div>
        </div>
    );
}
