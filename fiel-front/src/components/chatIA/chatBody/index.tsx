'use client';

import { BookResponse } from '@/api/dtos/responseDTOs';
import Image from "next/image";
import { useEffect, useRef, useState } from 'react';
import bookImage from '../../../../public/book.png';
import styles from './chatBody.module.css';

type Message = {
    sender: 'user' | 'bot';
    text: string;
};

type Step = "chooseFilter" | "typingValue";

// ⭐ ADICIONANDO "year" AQUI
type SelectedFilter = "genre" | "author" | "publisher" | "price" | "year" | null;

export default function ChatBody() {
    const [messages, setMessages] = useState<Message[]>([
        { sender: "bot", text: "Olá! O que você deseja filtrar? 👇" },
        { sender: "bot", text: "1️⃣ Gênero\n2️⃣ Autor\n3️⃣ Editora\n4️⃣ Preço máximo\n5️⃣ Ano de lançamento" }
    ]);

    const [input, setInput] = useState('');
    const [step, setStep] = useState<Step>("chooseFilter");
    const [selectedFilter, setSelectedFilter] = useState<SelectedFilter>(null);
    const bottomRef = useRef<HTMLDivElement | null>(null);

    const [selectedBook, setSelectedBook] = useState<BookResponse | null>(null);

    useEffect(() => {
        bottomRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    function addMessage(sender: 'user' | 'bot', text: string) {
        setMessages(prev => [...prev, { sender, text }]);
    }

    async function handleSend() {
        if (!input.trim()) return;

        const userText = input.trim();
        addMessage("user", userText);

        // ----------------------------
        // 1) ESCOLHER O FILTRO
        // ----------------------------
        if (step === "chooseFilter") {
            let filter: SelectedFilter = null;

            if (userText === "1") filter = "genre";
            if (userText === "2") filter = "author";
            if (userText === "3") filter = "publisher";
            if (userText === "4") filter = "price";
            if (userText === "5") filter = "year";   // ⭐ NOVO

            if (!filter) {
                addMessage("bot", "Opção inválida 😅 Tente novamente.");
            } else {
                setSelectedFilter(filter);
                setStep("typingValue");

                const question = {
                    genre: "Qual gênero você quer?",
                    author: "Qual autor você procura?",
                    publisher: "Qual editora?",
                    price: "Qual o preço máximo? (somente número)",
                    year: "Qual ano de lançamento? (somente número)" // ⭐ NOVO
                }[filter];

                addMessage("bot", question!);
            }

            setInput("");
            return;
        }

        // ----------------------------
        // 2) DIGITAR O VALOR DO FILTRO
        // ----------------------------
        if (step === "typingValue" && selectedFilter) {
            let filterValue: any = userText;

            // ⭐ validação numérica para price e year
            if (selectedFilter === "price" || selectedFilter === "year") {
                const number = Number(userText);
                if (isNaN(number)) {
                    addMessage("bot", "Digite apenas números. Ex: 1998 ou 50");
                    setInput("");
                    return;
                }
                filterValue = number;
            }

            addMessage("bot", "Perfeito! Buscando resultados… 🔍");

            // ENVIA {type, value}
            const res = await fetch("/api/recommend", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    type: selectedFilter,
                    value: filterValue
                })
            });

            const data = await res.json();

            if (data.reply && typeof data.reply === "object") {
                setSelectedBook(data.reply);
            } else {
                addMessage("bot", "Nenhum livro encontrado 😅");
            }

            // Resetar fluxo
            setStep("chooseFilter");
            setSelectedFilter(null);
            addMessage("bot", "Escolha outro filtro:\n1️⃣ Gênero\n2️⃣ Autor\n3️⃣ Editora\n4️⃣ Preço máximo\n5️⃣ Ano de lançamento");
            setInput("");
            return;
        }
    }

    function handleReturn() {
        setSelectedBook(null);
        setStep("chooseFilter");
        setSelectedFilter(null);

        const lastMessage = messages[messages.length - 1]?.text;

        const filterText =
            "Escolha outro filtro:\n1️⃣ Gênero\n2️⃣ Autor\n3️⃣ Editora\n4️⃣ Preço máximo\n5️⃣ Ano de lançamento";

        if (lastMessage !== filterText) {
            addMessage("bot", filterText);
        }
    }


    return (
        <div className={styles.container}>
            <div className={styles.chatBody}>
                {messages.map((msg, index) => (
                    <div
                        key={index}
                        className={
                            msg.sender === 'user'
                                ? styles.userMessage
                                : styles.botMessage
                        }
                    >
                        {msg.text}
                    </div>
                ))}
                <div ref={bottomRef}></div>
            </div>

            <footer className={styles.chatFooter}>
                <input
                    className={styles.textBox}
                    type="text"
                    placeholder="Digite aqui..."
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSend()}
                />
                <button onClick={handleSend} className={styles.sendButton}>
                    ➤
                </button>
            </footer>

            {selectedBook && (
                <div className={styles.bookCard}>
                    <Image
                        src={bookImage}
                        alt={selectedBook.name}
                        className={styles.bookImage}
                        width={120}
                        height={180}
                    />

                    <h2 className={styles.bookTitle}>{selectedBook.name}</h2>

                    <p><b>Autor:</b> {selectedBook.author || "Não informado"}</p>

                    <p>
                        <b>Categorias:</b>{" "}
                        {selectedBook.categories?.length
                            ? selectedBook.categories.join(", ")
                            : "Não informado"}
                    </p>

                    <p><b>Ano de lançamento:</b> {selectedBook.year || "—"}</p>

                    <p>
                        <b>Preço:</b>{" "}
                        {typeof selectedBook.price === "number"
                            ? `R$ ${selectedBook.price.toFixed(2)}`
                            : "—"}
                    </p>

                    <div className={styles.buttonRow}>
                        <a
                            href={`/book?bookId=${selectedBook.id}`}
                            className={styles.openButton}
                        >
                            Ver livro 📚
                        </a>

                        <button
                            onClick={handleReturn}
                            className={styles.returnButton}
                        >
                            Voltar ⬅️
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}
