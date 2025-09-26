import styles from './actionButton.module.css';

interface ActionButtonProps {
    label: string;
    onClick: () => void;
    color?: string;
    dataCy?: string;
}

const ActionButton: React.FC<ActionButtonProps> = ({ label, onClick, color = "#e63946", dataCy }) => {
    return (
        <button 
            className={styles.button} 
            onClick={onClick}
            style={{ backgroundColor: color }}
            data-cy={dataCy}
        >
            {label}
        </button>
    );
}

export default ActionButton;