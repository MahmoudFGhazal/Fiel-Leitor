'use server'
import Table from "@/components/table";
import styles from './page.module.css';

export default async function Dashboard() {
    return (
        <div className={styles.container}>
            <Table data={[
                { date: '2025-10-01', value: 320 },
                { date: '2025-10-02', value: 540 },
                { date: '2025-10-03', value: 410 },
            ]} />
        </div>
    );
}