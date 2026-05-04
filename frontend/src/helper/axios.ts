import axios from 'axios';

const getBaseURL = () => {
    const path = window.location.pathname;
    if (path.includes("login") || path.includes("register") || path.includes("reset-password")) {
        return "http://localhost:8082";
    }
    return "http://localhost:8080";
};

const axiosInstance = axios.create({
    baseURL: getBaseURL(),
    headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
    }
});

axiosInstance.interceptors.request.use((config) => {
    config.baseURL = getBaseURL();
    
    const userId = localStorage.getItem('USER_ID');
    if (userId) {
        config.headers['X-User-Id'] = userId;
    }
    
    return config;
});

export default axiosInstance;
