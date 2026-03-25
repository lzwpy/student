import request from "./request";
export function getClassroomsApi() {
    return request.get("/classrooms");
}
export function createClassroomApi(payload) {
    return request.post("/classrooms", payload);
}
export function renameClassroomApi(id, payload) {
    return request.put(`/classrooms/${id}`, payload);
}
