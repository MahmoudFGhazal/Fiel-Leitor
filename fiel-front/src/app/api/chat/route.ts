import { BookResponse } from "@/api/dtos/responseDTOs";
import { ApiResponse } from "@/api/objects";
import api from "@/api/route";
import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
    try {
        const { message } = await req.json();

        // 1) Buscar livros do seu backend
        const res = await api.get<ApiResponse>('/book/active');

        if (!res?.data?.entities) {
            return NextResponse.json({ reply: "Erro ao carregar livros 😅" });
        }

        const entities = res.data?.entities as unknown as BookResponse[];

        // Formatando para IA
        const livrosTexto = entities
            .map(b => `• ${b.name} — ${b.author} — R$ ${b.price}`)
            .join("\n");

        const entrada = `
Você é uma IA recomendadora de livros.
Aqui está o catálogo disponível:

${livrosTexto}

Agora responda a pergunta do usuário:
"${message}"

IMPORTANTE:
- Recomende SOMENTE livros da lista acima.
- Cite o nome EXATO do livro que está na lista.
        `;

        // 2) Enviar para Fireworks
        const fwRes = await fetch(
            "https://api.fireworks.ai/inference/v1/chat/completions",
            {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${process.env.FIREWORKS_API_KEY}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    model: "accounts/fireworks/models/llama-v3p1-8b-instruct",
                    messages: [{ role: "user", content: entrada }]
                }),
            }
        );

        const data = await fwRes.json();
        const reply = data?.choices?.[0]?.message?.content ?? "Não consegui responder 😅";

        // 3) Identificar qual livro foi recomendado
        let link: string | null = null;

        for (const livro of entities) {
            const nome = livro.name.toLowerCase();
            console.log(nome)
            if (reply.toLowerCase().includes(nome)) {
                link = `/book?bookId=${livro.id}`;
                break;
            }
        }
        console.log(link)
        return NextResponse.json({ reply, link });

    } catch (error) {
        console.error("ERRO API CHAT:", error);
        return NextResponse.json({ reply: "Erro interno 😥" });
    }
}
