
export interface CartItem {
    id: number;
    name: string;
    price: number;
    quantity: number;
}

export interface BookData {
    id: number,
    name: string,
    categories: Categories[],
    price: number,
    author: string,
    description: string,
    stock: number,
    isActive: boolean
}

export enum Categories {
    SUSPENSE = 'suspense',
    COMEDY = 'comedy',
    ADVENTURE = 'adventure',
    ROMANCE = 'romance'
}
