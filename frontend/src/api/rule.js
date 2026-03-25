import request from "./request";
export function getRulesApi() {
    return request.get("/rules");
}
export function createRuleApi(payload) {
    return request.post("/rules", payload);
}
export function updateRuleApi(id, payload) {
    return request.put(`/rules/${id}`, payload);
}
export function deleteRuleApi(id) {
    return request.delete(`/rules/${id}`);
}
