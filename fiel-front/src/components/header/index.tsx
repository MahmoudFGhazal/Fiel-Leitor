'use client'
import { FaHome, FaShoppingCart, FaDoorOpen, FaSearch } from "react-icons/fa";
import { FaGear } from "react-icons/fa6";
import { IoLogIn } from "react-icons/io5";
import Image from 'next/image';
import logo from '@/../public/whitelogo.png';
import styles from './header.module.css';
import { useEffect, useState } from 'react';
import CartSidebar from "./cart";
import Link from "next/link";

const size = 20;

export default function Header(){
    const [searchQuery, setSearchQuery] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isCartOpen, setIsCartOpen] = useState(false);

    useEffect(() => {
        const currentUser =
            JSON.parse(localStorage.getItem('currentUser') || 'null') ||
            JSON.parse(sessionStorage.getItem('currentUser') || 'null');

        if (currentUser) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }, []);

    const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        alert(`Buscando por: ${searchQuery}`);
    };

    const handleLogout = () => {
        localStorage.removeItem('currentUser');
        sessionStorage.removeItem('currentUser');
        setIsLoggedIn(false);
        window.location.href = '/login'; 
    };

    const toggleCart = () => {
        setIsCartOpen(!isCartOpen);
    };

    return (
        <>
            <header className={styles.header}>
                <div className={styles.left}>
                    <div className={styles.logo}>
                        <Image src={logo} alt="Fiel Leitor Logo" className={styles.logoImage} />
                        <Link  href="/" className={styles.link}>
                            <FaHome size={size} />
                        </Link >
                    </div>
                </div>
                <div className={styles.center}>
                    <form onSubmit={handleSearch} className={styles.searchForm}>
                        <input
                            type="text"
                            placeholder="Pesquisar"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            className={styles.searchInput}
                        />
                        <button type="submit" className={styles.searchButton}>
                            <FaSearch style={{ color: 'black' }} size={17} />
                        </button>
                    </form>
                </div>
                <div className={styles.right}>
                    {isLoggedIn ? (
                        <div className={styles.userHeaderOptions}>
                            <p className={styles.cartButton} onClick={toggleCart}>
                                <FaShoppingCart size={size} />    
                            </p>
                            <a href="/config" className={styles.link}>
                                <FaGear size={size} />
                            </a>
                            <p className={styles.logoutButton} onClick={handleLogout}>
                                <FaDoorOpen size={size} />
                            </p>
                        </div>
                    ) : (
                        <div className={styles.userHeaderOptions}>
                            <a href="/login" className={styles.link}>
                                <IoLogIn size={size} />
                            </a>
                        </div>
                    )}
                </div>
            </header>
            {isCartOpen && (
                <CartSidebar onClose={toggleCart} />
            )}
        </>
    );
}