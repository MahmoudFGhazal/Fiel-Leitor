'use server'
import OrderCard from '@/components/order/orderCard';
import styles from './page.module.css';

const pedidosData = [
    {
        orderNumber: '701-7679356-2482618',
        date: '4 de dezembro de 2023',
        total: 'R$ 179,88',
        items: [
            { name: 'Danone Nutricia Nutridrink - Suplemento Protein, Baunilha, 200ml', quantity: 8, image: '/vanilla.png' },
            { name: 'Suplemento Nutridrink Protein Chocolate Danone Nutricia 200ml', quantity: 4, image: '/chocolate.png' },
        ]
    },
    {
        orderNumber: '701-2463674-4023468',
        date: '15 de novembro de 2023',
        total: 'R$ 205,95',
        items: [
            { name: 'Danone Nutricia Nutridrink - Suplemento Protein, Baunilha, 200ml', quantity: 15, image: '/vanilla.png' },
        ]
    },
    {
        orderNumber: '701-9155697-1590609',
        date: '4 de novembro de 2023',
        total: 'R$ 282,00',
        items: [
            { name: 'Controle Dualshock 4 - PlayStation 4 - Preto', quantity: 1, image: '/dualshock.png' },
        ]
    }
];

export default async function Historical() {
    return (
        <div>
            <h1>Seus pedidos</h1>
            {pedidosData.map((order) => (
                <OrderCard key={order.orderNumber} {...order} />
            ))}
        </div>
    );
}