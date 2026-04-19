import axios from 'axios';

const getBaseURL = () => {
    const path = window.location.pathname;
    // If we are on the login page, register page, or forgot password, talk to the Auth Service (8082)
    if (path.includes("login") || path.includes("register") || path.includes("forgot-password")) {
        return "http://localhost:8082";
    }
    // Otherwise, talk to the Main Service (8080)
    return "http://localhost:8080";
};

const axiosInstance = axios.create({
    baseURL: getBaseURL(),
    headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
    }
});

// Update baseURL on each request in case the path changed
axiosInstance.interceptors.request.use((config) => {
    config.baseURL = getBaseURL();
    return config;
});

export default axiosInstance;
