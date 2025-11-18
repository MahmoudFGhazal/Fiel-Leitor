import { ApiResponse } from "@/api/objects";
import api from "@/api/route";
import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
    const body = await req.json();

    if (!body.type || body.value === undefined) {
        return NextResponse.json({
            reply: "Requisição inválida 😅 (faltou type ou value)",
        });
    }

    // Requisição: buscar TODOS os livros
    const response = await api.get<ApiResponse>("/book/all");
    const allBooks = response.data.entities;

    if (!Array.isArray(allBooks)) {
        return NextResponse.json({ reply: [] });
    }

    const books = filterBooks(body.type, body.value, allBooks);

    return NextResponse.json({
        reply: books // sempre lista
    });
}

function filterBooks(type: string, value: any, books: any[]) {
    switch (type) {
        case "genre":
            return books.filter(b => b.categories?.includes(Number(value)));

        case "author":
            return books.filter(b =>
                b.author?.toLowerCase().includes(value.toLowerCase())
            );

        case "publisher":
            return books.filter(b =>
                b.publisher?.toLowerCase().includes(value.toLowerCase())
            );

        case "price":
            return books.filter(b => b.price <= Number(value));

        case "year":
            return books.filter(b => b.year === Number(value));

        default:
            return [];
    }
}
