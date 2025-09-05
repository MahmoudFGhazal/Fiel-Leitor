import styles from './footer.module.css';

export default function Footer(){
    return (
        <footer className={styles.footer}>
            <p>© {new Date().getFullYear()} Fiel Leitor. Todos os direitos reservados.</p>
        </footer>
    );
}