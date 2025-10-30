'use server'
import ButtonLink from '@/components/buttonComponents/buttonLink';
import AddressConfig from '@/components/config/addressConfig';
import CardConfig from '@/components/config/cardConfig';
import ChangePasswordConfig from '@/components/config/changePasswordConfig';
import ProfileConfig from '@/components/config/profileConfig';
import CardConfig from '@/components/config/couponConfig';
import styles from './page.module.css';

type Tab = 'profile' | 'addresses' | 'cards' | 'password' | 'coupons';

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
                <ButtonLink text="Profile" href="?tab=profile" />
                <ButtonLink text="Alterar Senha" href="?tab=password" />
                <ButtonLink text="Endereços" href="?tab=addresses" />
                <ButtonLink text="Cartões" href="?tab=cards" />
                <ButtonLink text="Botões" href="?tab=coupons" />
            </div>

            <div className={styles.tabContent}>
                {activeTab === 'profile' && <ProfileConfig />}
                {activeTab === 'password' && <ChangePasswordConfig />}
                {activeTab === 'addresses' && <AddressConfig />}
                {activeTab === 'cards' && <CardConfig />}
                {activeTab === 'coupons' && <CouponConfig />}
            </div>
        </div>
    );
}