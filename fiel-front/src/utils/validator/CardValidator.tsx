// src/utils/validator/CardValidator.ts
export function parseCardData(cardNumber: string, expDate: string) {
    const cleanNumber = (cardNumber ?? '').replace(/\D/g, '');
    const cleanExp = (expDate ?? '').replace(/\D/g, '');

    const bin = cleanNumber.slice(0, 6);
    const last4 = cleanNumber.length >= 4 ? cleanNumber.slice(-4) : '';

    let expMonth = '';
    let expYear = '';

    if (cleanExp.length >= 2) {
        expMonth = cleanExp.slice(0, 2);
    }

    if (cleanExp.length === 4) {
        expYear = `20${cleanExp.slice(2, 4)}`;
    } else if (cleanExp.length === 6) {
        expYear = cleanExp.slice(2, 6);
    } else if (cleanExp.length > 4) {
        expYear = cleanExp.slice(2);
        if (expYear.length === 2) {
            expYear = `20${expYear}`;
        }
    }

    if (expMonth) {
        const m = Number(expMonth);
        if (Number.isNaN(m) || m < 1 || m > 12) {
            expMonth = '';
            expYear = '';
        }
    }

    return { bin, last4, expMonth, expYear };
}
