import { ApiResponse, Gender, ResidenceType, StreetType } from "@/api/objects";
import api from "@/api/route";

export async function getGenders(): Promise<Gender[]> {
    const response = await api.get<ApiResponse>(`/gender/all`);
    return (response.data.entities as Gender[]) ?? [];
}

export async function getStreetTypes(): Promise<StreetType[]> {
    const response = await api.get<ApiResponse>(`/streetType/all`);
    return (response.data.entities as StreetType[]) ?? [];
}

export async function getResidenceTypes(): Promise<ResidenceType[]> {
    const response = await api.get<ApiResponse>(`/residenceType/all`);
    return (response.data.entities as ResidenceType[]) ?? [];
}


