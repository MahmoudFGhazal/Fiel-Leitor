'use client'
import logo from '@/../public/whitelogo.png';
import { useGlobal } from "@/context/GlobalContext";
import Image from 'next/image';
import Link from "next/link";
import { useState } from 'react';
import { FaArchive, FaDoorOpen, FaHome, FaSearch, FaShoppingCart } from "react-icons/fa";
import { FaGear } from "react-icons/fa6";
import { IoLogIn } from "react-icons/io5";
import CartSidebar from "./cart";
import styles from './header.module.css';

export default function Header(){
    const [searchQuery, setSearchQuery] = useState('');
    const [isCartOpen, setIsCartOpen] = useState(false);
    const { currentUser, setCurrentUser } = useGlobal();

    const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        alert(`Buscando por: ${searchQuery}`);
    };

    const handleLogout = () => {
        localStorage.removeItem('currentUser');
        setCurrentUser(0);
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
                            <FaHome size={20} />
                        </Link >
                        <Link  href="/historical" className={styles.link}>
                            <FaArchive size={18} />
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
                    {currentUser ? ( 
                        <div className={styles.userHeaderOptions}>
                            <p className={styles.cartButton} onClick={toggleCart} data-cy="open-cart-button" >
                                <FaShoppingCart size={20} />    
                            </p>
                            <Link href="/config" className={styles.link}>
                                <FaGear size={20} />
                            </Link>
                            <p className={styles.logoutButton} onClick={handleLogout}>
                                <FaDoorOpen size={20} />
                            </p>
                        </div>
                    ) : (
                        <div className={styles.userHeaderOptions}>
                            <Link href="/login" className={styles.link}>
                                <IoLogIn size={20} />
                            </Link>
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