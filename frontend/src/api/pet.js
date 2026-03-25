import request from "./request";
export function adoptPetApi(studentId, payload) {
    return request.post(`/students/${studentId}/pet`, payload);
}
export function getPetApi(studentId) {
    return request.get(`/students/${studentId}/pet`);
}
