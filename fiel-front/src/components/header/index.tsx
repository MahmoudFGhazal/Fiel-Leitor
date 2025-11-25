'use client'
import logo from '@/../public/whitelogo.png';
import { useGlobal } from "@/context/GlobalContext";
import Image from 'next/image';
import Link from "next/link";
import { useState } from 'react';
import { AiOutlineProduct } from "react-icons/ai";
import { FaArchive, FaDoorOpen, FaHome, FaSearch, FaShoppingCart, FaTable } from "react-icons/fa";
import { FaGear } from "react-icons/fa6";
import { HiUsers } from "react-icons/hi";
import { IoLogIn } from "react-icons/io5";
import CartSidebar from "./cart";
import styles from './header.module.css';

export default function Header(){
    const [searchQuery, setSearchQuery] = useState('');
    const [isCartOpen, setIsCartOpen] = useState(false)
    ;
    const { currentUser, isAdmin, logout } = useGlobal();

    const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        alert(`Buscando por: ${searchQuery}`);
    };

    const toggleCart = () => {
        setIsCartOpen(!isCartOpen);
    };

    if (currentUser && isAdmin) {
        return (
            <header className={styles.header}>
                <div className={styles.left}>
                    <Image src={logo} alt="Logo" className={styles.logoImage} />
                    <Link href="/dashboard" className={styles.link}><FaTable size={20} /></Link>
                    <Link href="/crud/client" className={styles.link}><HiUsers size={18} /></Link>
                    {/*<Link href="/crud/book" className={styles.link}><AiOutlineProduct size={18} /></Link>*/}
                </div>

                <div className={styles.right}>
                    <Link href="/control?tab=peding" className={styles.link}><AiOutlineProduct size={18} /></Link>
                    <p className={styles.logoutButton} onClick={logout}><FaDoorOpen size={20} /></p>
                </div>
            </header>
        );
    }

    if (currentUser && !isAdmin) {
        return (
            <>
                <header className={styles.header}>
                    <div className={styles.left}>
                        <div className={styles.logo}>
                            <Image src={logo} alt="Fiel Leitor Logo" className={styles.logoImage} />
                            <Link href="/" className={styles.link}><FaHome size={20} /></Link>
                            <Link href="/historical" className={styles.link}><FaArchive size={18} /></Link>
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
                        <div className={styles.userHeaderOptions}>
                            <p className={styles.cartButton} onClick={toggleCart} data-cy="open-cart-button">
                                <FaShoppingCart size={20} />
                            </p>
                            <Link href="/config" className={styles.link}><FaGear size={20} /></Link>
                            <p className={styles.logoutButton} onClick={logout}><FaDoorOpen size={20} /></p>
                        </div>
                    </div>
                </header>

                {isCartOpen && <CartSidebar onClose={toggleCart} />}
            </>
        );
    }

    return (
        <header className={styles.header}>
            <div className={styles.left}>
                <Image src={logo} alt="Logo" className={styles.logoImage} />
                <Link href="/" className={styles.link}><FaHome size={20} /></Link>
            </div>
            <div className={styles.right}>
                <Link href="/login" className={styles.link}>
                    <IoLogIn size={20} />
                </Link>
            </div>
        </header>
    );
}