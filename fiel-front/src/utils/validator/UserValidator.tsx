import { AddressRequest, UserRequest } from "@/api/dtos/requestDTOs";

interface formData {
    user: UserRequest,
    address: AddressRequest
}

export function createAccountValidator() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{8,}$/;
    const cpfRegex = /^\d{11}$/;

    const onlyNumbers = (str: string) => str.replace(/\D/g, '');

    const validateStep = (step: number, data: formData, extra?: any): string | null => {
        const user = data.user;
        const addr = data.address;

        switch (step) {
            case 1:
                if (!user.email || !emailRegex.test(user.email)) return "Email inválido";
                if (!user.password || !passwordRegex.test(user.password)) return "Senha inválida. Deve ter ao menos 8 caracteres, letras maiúsculas e minúsculas e caracteres especiais.";
                if (!extra?.confirmPassword || extra?.confirmPassword!== user.password) return "Senhas não conferem";
                return null;

            case 2: 
                if (!user.name || user.name.trim().length === 0) return "Nome é obrigatório";
                if (!user.birthday) return "Data de nascimento é obrigatória";

                if (!user.cpf || !cpfRegex.test(onlyNumbers(user.cpf))) return "CPF inválido";
                if (!user.phoneNumber || onlyNumbers(user.phoneNumber).length < 10) return "Telefone inválido";

                return null;

            case 3: 
                if (!addr) return "Endereço é obrigatório";

                if (!addr.nickname || !addr.zip || !addr.street || !addr.number || !addr.neighborhood || !addr.city || !addr.state || !addr.country || !addr.streetType || !addr.residenceType)
                    return "Todos os campos do endereço são obrigatórios, exceto complemento";

                addr.zip = onlyNumbers(addr.zip);

                return null;

            default:
                return null;
        }
    };

    return { validateStep, onlyNumbers };
}

export function changePasswordValidator() {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{8,}$/;

    const validate = (currentPassword: string, newPassword: string, confirmPassword: string): string | null => {
        if (!currentPassword || currentPassword.trim().length === 0) {
            return "Senha atual é obrigatória";
        }

        if (!newPassword || !passwordRegex.test(newPassword)) {
            return "Nova senha inválida. Deve ter ao menos 8 caracteres, letras maiúsculas e minúsculas e caracteres especiais.";
        }

        if (!confirmPassword || confirmPassword !== newPassword) {
            return "Confirmação da nova senha não confere";
        }

        return null;
    };

    return { validate };
}
