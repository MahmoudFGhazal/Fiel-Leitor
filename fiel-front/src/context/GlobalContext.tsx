"use client";
import { createContext, useContext, useState, ReactNode, useEffect } from "react";

type GlobalContextType = {
  currentUser: number;
  setCurrentUser: (t: number) => void;
};

const GlobalContext = createContext<GlobalContextType | undefined>(undefined);

export function GlobalProvider({ children }: { children: ReactNode }) {
    const [currentUser, setCurrentUser] = useState<number>(0);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem("currentUser") || "0");
        if (storedUser) {
            setCurrentUser(storedUser);
        }
    }, []);

    return (
        <GlobalContext.Provider value={{ currentUser, setCurrentUser }}>
            <main className="content">{children}</main>
        </GlobalContext.Provider>
    );
}

export function useGlobal() {
    const ctx = useContext(GlobalContext);
    if (!ctx) throw new Error("useGlobal deve estar dentro de GlobalProvider");
    return ctx;
}
