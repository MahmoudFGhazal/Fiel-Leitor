import Image, { StaticImageData } from "next/image";
import styles from "./Field.module.css";

interface FieldProps {
    name: string;
    price: number;
    image: StaticImageData;
}

export default function Field({ name, price, image }: FieldProps) {
    return (
        <div className={styles.field}>
            <div className={styles.imageWrapper}>
                <Image
                    src={image}
                    alt={name}
                    className={styles.fieldImage}
                />
            </div>
            <h3 className={styles.fieldName}>{name}</h3>
            <p className={styles.fieldPrice}>R$ {price.toFixed(2)}</p>
        </div>
    );
}
