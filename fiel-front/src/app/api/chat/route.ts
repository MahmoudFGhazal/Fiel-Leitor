import { BookResponse } from "@/api/dtos/responseDTOs";
import { ApiResponse } from "@/api/objects";
import api from "@/api/route";
import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
    try {
        const { message } = await req.json();

        const res = await api.get<ApiResponse>('/book/active');

        if (!res?.data?.entities) {
            return NextResponse.json({ reply: "Erro ao carregar livros 😅" });
        }

        const entities = res.data.entities as unknown as BookResponse[];

        const livrosTexto = entities
            .map(b => `• ${b.name} — ${b.author} — R$ ${b.price} - ${b.year} - ${b.categories.map(c => c.category).join(", ")} - ${b.pages}`)
            .join("\n");

        const entrada = `
Você é uma IA recomendadora de livros.
Aqui está o catálogo disponível:

${livrosTexto}

Agora responda à pergunta do usuário:
"${message}"

REGRAS LÓGICAS OBRIGATÓRIAS:
1. Antes de recomendar um livro, você DEVE verificar objetivamente se ele atende ao critério do usuário.
2. Se o critério envolver números (páginas, preço, ano), você DEVE usar os números reais fornecidos no catálogo acima.
3. Você está TERMINANTEMENTE proibida de inventar valores, aproximar ou reinterpretar.  
   Exemplo: "560 é menor que 200" nunca deve acontecer.
4. Se NENHUM livro atender ao critério, responda exatamente:
"❌ Nenhum livro atende ao critério solicitado."
5. Nunca sugira livros que não aparecem no catálogo.

FORMATO OBRIGATÓRIO DA RESPOSTA:
Se houver recomendação:
<b>📘 Livro recomendado:</b> NOME DO LIVRO — AUTOR<br/>
<b>Motivo:</b> Explique em UMA LINHA como ele atende ao critério real.<br/>

Se não houver recomendação:
❌ Nenhum livro atende ao critério solicitado.

IMPORTANTE:
- Escolha APENAS um livro.
- Use o nome EXATO do catálogo.
- Seja breve.
`;



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

        const replyRaw = data?.choices?.[0]?.message?.content ?? "Não consegui responder 😅";
        const reply = replyRaw.trim();

        let link: string | null = null;

        for (const livro of entities) {
            const nome = livro.name.toLowerCase();
            if (reply.toLowerCase().includes(nome)) {
                link = `/book?bookId=${livro.id}`;
                break;
            }
        }

        return NextResponse.json({ reply, link });

    } catch (error) {
        console.error("ERRO API CHAT:", error);
        return NextResponse.json({ reply: "Erro interno 😥" });
    }
}
