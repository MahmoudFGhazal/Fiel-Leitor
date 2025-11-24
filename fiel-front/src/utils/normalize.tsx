import { StatusSale } from "@/translate/base";

export function normalizeStatus(status?: string | null): StatusSale | null {
    if (!status) return null;

    const normalized = status.toLowerCase();

    const matchValue = Object.values(StatusSale).find(v => v.toLowerCase() === normalized);
    if (matchValue) return matchValue as StatusSale;

    const upper = status.toUpperCase();
    if (upper in StatusSale) {
        return StatusSale[upper as keyof typeof StatusSale];
    }

    return null;
}
