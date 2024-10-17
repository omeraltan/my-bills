import axios from 'axios';

// Axios örneği oluşturuluyor
const api = axios.create({
    baseURL: 'http://localhost:9090/api/v1/analiz', // Backend'inizin base URL'sini buraya yazın
    headers: {
        'Content-Type': 'application/json',
    },
});

// İstek öncesi yapılacak ayarlar (Opsiyonel)
api.interceptors.request.use(
    (config) => {
        // Token eklemek isterseniz
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Yanıtları yönetmek için (Opsiyonel)
api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        // Hataları yönetmek için
        return Promise.reject(error);
    }
);

export default api;
