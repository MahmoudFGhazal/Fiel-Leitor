import { BookRequest } from "@/api/dtos/requestDTOs";
import api from "@/api/route";
import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
    const body = await req.json();
    console.log("Body recebido:", body);

    if (!body.type || body.value === undefined) {
        return NextResponse.json({
            reply: "Requisição inválida 😅 (faltou type ou value)"
        });
    }

    const bookRequest = buildBookRequest(body.type, body.value);

    console.log("Payload enviado para o backend:", bookRequest);

    const response = await api.post<{ reply: string }>(
        "/book/recommend",
        { data: bookRequest }
    );

    return NextResponse.json({
        reply: response.reply || "Não encontrei nada 😅",
    });
}

function buildBookRequest(type: string, value: any) {
    const base: BookRequest = {
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
        active: true,
        priceGroupId: 0,
        categories: []
    };

    switch (type) {
        case "genre":
            base.categories = [Number(value) || 0]; // ou ID da categoria
            break;

        case "author":
            base.author = value;
            break;

        case "publisher":
            base.publisher = value;
            break;

        case "price":
            base.price = Number(value);
            break;
    }

    return base;
}
