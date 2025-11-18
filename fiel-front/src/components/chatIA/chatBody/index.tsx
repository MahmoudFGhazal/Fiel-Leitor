'use client';

import { useEffect, useRef, useState } from "react";
import styles from './chatBody.module.css';

export default function ChatBody() {
    const [messages, setMessages] = useState([
        { sender: "bot", text: "Oi! 👋 Sou sua IA. Pode conversar comigo sobre o que quiser 💬" }
    ]);

    const [input, setInput] = useState("");
    const bottomRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        bottomRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    function addMessage(sender: "user" | "bot", text: string) {
        setMessages(prev => [...prev, { sender, text }]);
    }

    // 🔗 Transformar textos com "/book?bookId=ID" em links clicáveis
    function formatMessage(text: string) {
        const urlRegex = /(\/book\?bookId=\d+)/g;

        return text.replace(urlRegex, (match) => {
            return `<a href="${match}" class="chatLink">${match}</a>`;
        });
    }

    // ✨ Agora o FRONT chama nossa API interna /api/chat
    async function sendToAI(prompt: string) {
        const response = await fetch("/api/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ message: prompt }),
        });

        const data = await response.json();
        return data; // <-- retorna {reply, link}
    }

    async function handleSend() {
        if (!input.trim()) return;

        const userMsg = input.trim();
        addMessage("user", userMsg);
        setInput("");

        addMessage("bot", "✍️ Processando...");

        const data = await sendToAI(userMsg);
        console.log("DATA RECEBIDA:", data);

        // remove "bot digitando"
        setMessages(prev => prev.filter(m => m.text !== "✍️ Processando..."));

        // mensagem da IA
        addMessage("bot", data.reply);

        // link extra (se existir)
        if (data.link) {
            addMessage("bot", `🔗 Livro recomendado: ${data.link}`);
        }
    }

    return (
        <div className={styles.container}>
            <div className={styles.chatBody}>
                {messages.map((msg, index) => (
                    <div
                        key={index}
                        className={
                            msg.sender === "user"
                                ? styles.userMessage
                                : styles.botMessage
                        }
                    >
                        {/* Renderiza texto com links clicáveis */}
                        <div
                            dangerouslySetInnerHTML={{
                                __html: formatMessage(msg.text)
                            }}
                        />
                    </div>
                ))}

                <div ref={bottomRef}></div>
            </div>

            <footer className={styles.chatFooter}>
                <input
                    className={styles.textBox}
                    type="text"
                    placeholder="Digite aqui…"
                    value={input}
                    onChange={e => setInput(e.target.value)}
                    onKeyDown={e => e.key === "Enter" && handleSend()}
                />

                <button onClick={handleSend} className={styles.sendButton}>
                    ➤
                </button>
            </footer>
        </div>
    );
}
