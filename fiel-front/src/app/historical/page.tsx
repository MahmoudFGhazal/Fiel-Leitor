'use server'
import ListSales from "@/components/listSales";

export default async function Historical() {
    return (
        <div>
            <h1>Seus pedidos</h1>
            <ListSales />
        </div>
    );
}