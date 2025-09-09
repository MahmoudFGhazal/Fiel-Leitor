import { Datas } from "./objects";

type Method = "GET" | "POST" | "PUT" | "DELETE";

const BASE_URL = "http://localhost:8080";

async function request<T>(method: Method, url: string, data?: Datas): Promise<T> {
    //No Header: Colcoar o Authorization
    const options: RequestInit = {
        method,
        headers: {
            "Content-Type": "application/json",
        },
        body: data ? JSON.stringify(data) : undefined,
    };

    try{
        const res = await fetch(`${BASE_URL}${url}`, options);
        const json = await res.json();

        if(!res.ok){
            throw new Error("Error na Requisição");
        }

        if(json.type === "" ){
            const error = await res.text();
            throw new Error(error || "Erro na requisição");
        }

        if(json.typeResponse === "BACK_ERROR" || json.typeResponse === "SERVER_ERROR"){
            const error = json.message;
            throw new Error(error || "Erro no Back");
        }

        return json as T;
    } catch(error){
        console.error("Erro na Requisição", error);
        throw error;   
    }
}

const api = {
    get: <T>(url: string, data?: Datas) => request<T>("GET", url, data),
    post: <T>(url: string, data?: Datas) => request<T>("POST", url, data),
    put: <T>(url: string, data?: Datas) => request<T>("PUT", url, data),
    delete: <T>(url: string, data?: Datas) => request<T>("DELETE", url, data),
}

export default api;