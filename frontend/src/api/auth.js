import request from "./request";
export function loginApi(payload) {
    return request.post("/auth/login", payload);
}
export function registerApi(payload) {
    return request.post("/auth/register", payload);
}
export function changePasswordApi(payload) {
    return request.put("/auth/password", payload);
}
