'use server'
import ButtonLink from '@/components/buttonComponents/buttonLink';
import FinishedSales from '@/components/controlSales/finished';
import PedingSales from '@/components/controlSales/peding';
import TradeSales from '@/components/controlSales/trade';
import styles from './page.module.css';

type Tab = 'peding' | 'finished' | 'trade';

export default async function Config({
        searchParams,
    }: {
        searchParams: Promise<{ tab?: Tab }>;
}) {
    const params = await searchParams;
    const activeTab = params.tab ?? 'profile';

    return (
        <div className={styles.container}>
            <div className={styles.sideBar}>
                <ButtonLink text="Pendente" href="?tab=peding" />
                <ButtonLink text="Finalizado" href="?tab=finished" />
                <ButtonLink text="Troca" href="?tab=trade" />
            </div>

            <div className={styles.tabContent}>
                {activeTab === 'peding' && <PedingSales />}
                {activeTab === 'finished' && <FinishedSales />}
                {activeTab === 'trade' && <TradeSales />}
            </div>
        </div>
    );
}