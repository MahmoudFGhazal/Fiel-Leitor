'use client'

import { useState } from 'react';
import styles from './chatBody.module.css';

type Message = {
    sender: 'user' | 'bot';
    text: string;
};

export default function ChatBody() {
    const [messages, setMessages] = useState<Message[]>([]);
    const [input, setInput] = useState('');

    async function handleSend() {
        if (!input.trim()) return;

        setMessages((prev) => [...prev, { sender: "user", text: input }]);
        const res = await fetch("/api/recommend", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userMessage: input }),
        });
        const data = await res.json();

        setMessages((prev) => [
            ...prev,
            { sender: "bot", text: data.reply || "Algo deu errado 😅" },
        ]);

        setInput("");
    }

    return (
        <div className={styles.container}>
            <div className={styles.chatBody}>
                {messages.map((msg, index) => (
                <div
                    key={index}
                    className={
                    msg.sender === 'user' ? styles.userMessage : styles.botMessage
                    }
                >
                    {msg.text}
                </div>
                ))}
            </div>

            <footer className={styles.chatFooter}>
                <input
                    className={styles.textBox}
                    type="text"
                    placeholder="Digite sua mensagem..."
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSend()}
                />
                <button onClick={handleSend} className={styles.sendButton}>
                    ➤
                </button>
            </footer>
        </div>
    );
}