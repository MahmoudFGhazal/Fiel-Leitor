import { AddressRequest, BookRequest, CardRequest, CartRequest, UserRequest } from "@/api/dtos/requestDTOs";
import { AddressResponse, BookResponse, CardResponse, CartResponse, UserResponse } from "@/api/dtos/responseDTOs";


export function toRequestUser(user: UserResponse | null): UserRequest {
    if(!user) {
        return {
            id: null,
            email: null,
            password: null,
            newPassword: null,
            name: null,
            active: null,
            gender: null,
            birthday: null,
            cpf: null,
            phoneNumber: null
        };
    }
    
    return {
        id: user.id,
        email: user.email,
        password: null,
        newPassword: null,
        name: user.name,
        active: user.active,
        gender: user.gender?.id ?? null,
        birthday: user.birthday,
        cpf: user.cpf,
        phoneNumber: user.phoneNumber
    };
}

export function toRequestAddress(address: AddressResponse | null): AddressRequest {
    if(!address) {
        return {
            id: null,
            user: null,
            principal: null,
            nickname: null,
            number: null,
            complement: null,
            street: null,
            neighborhood: null,
            zip: null,
            city: null,
            state: null,
            country: null,
            streetType: null,
            residenceType: null
        };
    }
    
    return {
        id: address.id,
        user: address.user?.id ?? null,
        principal: address.principal,
        nickname: address.nickname,
        number: address.number,
        complement: address.complement,
        street: address.street,
        neighborhood: address.neighborhood,
        zip: address.zip,
        city: address.city,
        state: address.state,
        country: address.country,
        streetType: address.streetType?.id ?? null,
        residenceType: address.residenceType?.id ?? null
    }
}

export function toRequestCard(card: CardResponse | null): CardRequest {
    if(!card) {
        return {
            id: null,
            user: null,
            principal: null,
            bin: null,
            last4: null,
            holder: null,
            expMonth: null,
            expYear: null
        };
    }
    
    return {
        id: card.id,
        user: card.user?.id ?? null,
        principal: card.principal,
        bin: card.bin,
        last4: card.last4,
        holder: card.holder,
        expMonth: card.expMonth,
        expYear: card.expYear
    }
}

export function toRequestCart(cart: CartResponse | null): CartRequest {
    if(!cart) {
        return {
            user: null,
            book: null,
            quantity: null
        };
    }
    
    return {
        user: cart.user?.id ?? null,
        book: cart.book?.id ?? null,
        quantity: cart.quantity ?? null
    }
}


export function toRequestBook(book: BookResponse | null): BookRequest {
    if (!book) {
        return {
            id: null,

            name: "",
            author: "",
            publisher: "",
            edition: "",
            year: 0,

            isbn: "",
            barcode: "",
            synopsis: "",

            pages: 0,

            height: null,
            width: null,
            depth: null,
            weight: null,

            price: 0,
            stock: 0,
            active: false,

            priceGroupId: 0,
            categories: []
        };
    }

    return {
        id: book.id ?? null,

        name: book.name,
        author: book.author,
        publisher: book.publisher,
        edition: book.edition,
        year: book.year,

        isbn: book.isbn,
        barcode: book.barcode,
        synopsis: book.synopsis,

        pages: book.pages,

        height: book.height,
        width: book.width,
        depth: book.depth,
        weight: book.weight,

        price: book.price,
        stock: book.stock,
        active: book.active,

        priceGroupId: book.priceGroup?.id ?? 0,
        categories: book.categories
            ?.map(c => c.id)
            .filter((id): id is number => id !== null && id !== undefined) ?? []
    };
}
