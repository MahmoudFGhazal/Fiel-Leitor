


export default function showToast(msg: string): Promise<void> {
    return new Promise<void>((resolve) => {
        alert(msg);
        resolve();
    });
}