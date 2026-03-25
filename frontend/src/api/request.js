import axios from "axios";
const request = axios.create({
    baseURL: "/api",
    timeout: 10000
});
request.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
request.interceptors.response.use((response) => {
    const result = response.data;
    if (result.code !== 200) {
        return Promise.reject(new Error(result.msg));
    }
    return result.data;
}, (error) => Promise.reject(error));
export default request;
