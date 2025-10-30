'use client'

import styles from './chatBody.module.css';


export default function ChatBody() {
   

    return (
        <div className={styles.container} >
            <div className={styles.chatBody}>

            </div>
            <footer className={styles.chatFooter}>
                <input className={styles.textBox} type='text' >

                </input>
            </footer>
        </div>
    );
}