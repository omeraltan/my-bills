import api from './api';

// Fatura kaydetme işlemi
export const saveFatura = async (faturaData: any) => {
    try {
        const response = await api.post('/faturatip', faturaData);
        return response.data;
    } catch (error) {
        console.error('Fatura kaydedilemedi:', error);
        throw error;
    }
};

// Fatura bilgisi getirme işlemi
export const getFaturaById = async (id: string) => {
    try {
        const response = await api.get(`/faturalar/${id}`);
        return response.data;
    } catch (error) {
        console.error('Fatura bilgisi getirilemedi:', error);
        throw error;
    }
};
