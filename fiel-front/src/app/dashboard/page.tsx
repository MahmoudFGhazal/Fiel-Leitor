'use server'
import { SaleResponse } from "@/api/dtos/responseDTOs";
import { ApiResponse } from "@/api/objects";
import api from "@/api/route";
import Table from "@/components/table";
import styles from './page.module.css';

export default async function Dashboard() {
    const res = await api.get<ApiResponse>(`/sale/finished`);
    const entities = res.data?.entities as SaleResponse[] ?? [];
    console.log(entities)
    return (
        <div className={styles.container}>
            <Table data={entities} />
        </div>
    );
}