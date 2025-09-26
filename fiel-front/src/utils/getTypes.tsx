import { GenderResponse, ResidenceTypeResponse, StreetTypeResponse } from "@/api/dtos/responseDTOs";
import { ApiResponse } from "@/api/objects";
import api from "@/api/route";

export async function getGenders(): Promise<GenderResponse[]> {
    const response = await api.get<ApiResponse>(`/gender/all`);
    return (response.data.entities as GenderResponse[]) ?? [];
}

export async function getStreetTypes(): Promise<StreetTypeResponse[]> {
    const response = await api.get<ApiResponse>(`/streetType/all`);
    return (response.data.entities as StreetTypeResponse[]) ?? [];
}

export async function getResidenceTypes(): Promise<ResidenceTypeResponse[]> {
    const response = await api.get<ApiResponse>(`/residenceType/all`);
    return (response.data.entities as ResidenceTypeResponse[]) ?? [];
}


