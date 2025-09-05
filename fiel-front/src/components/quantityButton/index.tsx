'use client';
import { FaArrowUp, FaArrowDown } from 'react-icons/fa';
import styles from './quantityButtons.module.css'; 
import Button from '../button';

interface QuantityButtonsProps {
  quantity: number;
  onIncrease: () => void;
  onDecrease: () => void;
}

export default function QuantityButtons({ quantity, onIncrease, onDecrease }: QuantityButtonsProps) {
  return (
        <div className={styles.quantityControls}>
            <Button
                type='button'
                onClick={onDecrease}
            >
                <FaArrowDown size={15} />
            </Button>
            <span className={styles.quantity}>{quantity}</span>
            <Button
                type='button'
                onClick={onIncrease}
            >
                <FaArrowUp size={15} />
            </Button>
        </div>
  );
}
