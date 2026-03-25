import request from "./request";
export function getStudentsApi(classId) {
    return request.get(`/classrooms/${classId}/students`);
}
export function createStudentsApi(classId, payload) {
    return request.post(`/classrooms/${classId}/students`, payload);
}
export function renameStudentApi(id, payload) {
    return request.put(`/students/${id}`, payload);
}
export function deleteStudentApi(id) {
    return request.delete(`/students/${id}`);
}
