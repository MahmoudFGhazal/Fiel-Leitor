import styles from './actionButton.module.css';

interface ActionButtonProps {
    label: string;
    onClick: () => void;
}

const ActionButton: React.FC<ActionButtonProps> = ({ label, onClick }) => {
    return (
        <button 
            className={styles.button} 
            onClick={onClick}
        >
            {label}
        </button>
    );
}

export default ActionButton;