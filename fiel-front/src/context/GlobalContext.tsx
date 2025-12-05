"use client";
import { useRouter } from "next/navigation";
import { createContext, ReactNode, useContext, useEffect, useState } from "react";

type GlobalContextType = {
    currentUser: number;
    setCurrentUser: (t: number) => void;

    isAdmin: boolean;
    setIsAdmin: (v: boolean) => void;

    logout: () => void;
};

const GlobalContext = createContext<GlobalContextType | undefined>(undefined);

export function GlobalProvider({ children }: { children: ReactNode }) {
    const [currentUser, setCurrentUser] = useState<number>(0);
    const [isAdmin, setIsAdmin] = useState<boolean>(false);

    const router = useRouter(); 

    useEffect(() => {
        const cookieUser = getCookie("currentUser");
        const cookieAdmin = getCookie("isAdmin");

        if (cookieUser) {
            setCurrentUser(Number(cookieUser));
            setIsAdmin(cookieAdmin === "true");
            return;
        }

        const storedUser = Number(localStorage.getItem("currentUser"));
        const storedAdmin = localStorage.getItem("isAdmin") === "true";

        if (storedUser) setCurrentUser(storedUser);
        setIsAdmin(storedAdmin);
    }, []);

    const logout = () => {
        setCurrentUser(0);
        setIsAdmin(false);
        localStorage.removeItem("currentUser");
        localStorage.removeItem("isAdmin");

        router.push("/");
    };

    return (
        <GlobalContext.Provider value={{ currentUser, setCurrentUser, isAdmin, setIsAdmin, logout }}>
            {children}
        </GlobalContext.Provider>
    );
}

export function useGlobal() {
    const ctx = useContext(GlobalContext);
    if (!ctx) throw new Error("useGlobal deve estar dentro de GlobalProvider");
    return ctx;
}

function getCookie(name: string): string | null {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? match[2] : null;
}