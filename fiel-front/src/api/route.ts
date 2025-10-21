import { DatasRequest } from "./objects";

type Method = "GET" | "POST" | "PUT" | "DELETE";

interface RequestOptions {
    data?: DatasRequest;        
    params?: Record<string, unknown>;
}

const BASE_URL = "http://localhost:8080";

async function request<T>(method: Method, url: string, options?: RequestOptions): Promise<T> {
    let fullUrl = `${BASE_URL}${url}`;
    
    //No Header: Colcoar o Authorization
    if (options?.params) {
        const query = new URLSearchParams();
        Object.entries(options.params).forEach(([key, value]) => {
            if (value !== undefined && value !== null) query.append(key, value.toString());
        });
        fullUrl += `?${query.toString()}`;
    }

    const fetchOptions: RequestInit = {
        method,
        headers: {
            "Content-Type": "application/json",
        },
        body: options?.data ? JSON.stringify(options.data) : undefined,
    };

    try{
        const res = await fetch(fullUrl, fetchOptions);
        const json = await res.json();

        if (json.typeResponse === "SERVER_ERROR") {
            throw new Error(json.message || "Erro no Back");
        }

        return json as T;
    } catch(error){
        console.log(url);
        console.error("Erro na Requisição", error);
        throw error;   
    }
}

const api = {
    get: <T>(url: string, options?: RequestOptions) => request<T>("GET", url, options),
    post: <T>(url: string, options?: RequestOptions) => request<T>("POST", url, options),
    put: <T>(url: string, options?: RequestOptions) => request<T>("PUT", url, options),
    delete: <T>(url: string, options?: RequestOptions) => request<T>("DELETE", url, options),
};

export default api;