'use client'
import { useEffect, useRef, useState } from "react";
import { IoChatbubbleEllipsesOutline } from "react-icons/io5";
import ChatBody from "./chatBody";
import styles from './chatIA.module.css';


export default function ChatIA() {
    const [isOpen, setIsOpen] = useState(false);
    const chatRef = useRef<HTMLDivElement>(null);
    const buttonRef = useRef<HTMLButtonElement>(null);

    useEffect(() => {
        function handlePointerDown(e: PointerEvent) {
            if (!isOpen) return;

            const target = e.target as Node;

            const outsideChat =
                !!chatRef.current && !chatRef.current.contains(target);

            const outsideButton =
                buttonRef.current ? !buttonRef.current.contains(target) : true;

            if (outsideChat && outsideButton) {
                setIsOpen(false);
            }
        }

        function handleKeyDown(e: KeyboardEvent) {
            if (isOpen && e.key === 'Escape') setIsOpen(false);
        }

        document.addEventListener('pointerdown', handlePointerDown, true);
        document.addEventListener('keydown', handleKeyDown);
        return () => {
            document.removeEventListener('pointerdown', handlePointerDown, true);
            document.removeEventListener('keydown', handleKeyDown);
        };
    }, [isOpen]);

    return (
        <div className={styles.container} >
            {!isOpen ? (
                <button 
                    ref={buttonRef} 
                    className={styles.button} 
                    onClick={() => setIsOpen(true)}
                >
                    <IoChatbubbleEllipsesOutline />
                </button>
            ) : (
                 <div
                    ref={chatRef}
                    className={styles.chat}
                    role="dialog"
                    onClick={(e) => e.stopPropagation()} 
                >
                    <ChatBody />
                </div>
            )}
        </div>
    );
}