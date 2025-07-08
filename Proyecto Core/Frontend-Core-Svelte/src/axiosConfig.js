import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', // URL base del backend en Railway
    timeout: 10000, // Opcional: tiempo de espera para las solicitudes
});

export default axiosInstance;
