import api from "@/api/route";
import { NextRequest, NextResponse } from "next/server";

export async function POST(req: NextRequest) {
    const { userMessage } = await req.json();
    console.log("Mensagem recebida:", userMessage);

    const payload: chatRequest = { message: userMessage }

    const response = await api.post<{ reply: string }>(
        "/recommend",
        { data: payload }
    );

    return NextResponse.json({
        reply: response.reply || "Não encontrei nada 😅",
    });
}