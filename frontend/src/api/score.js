import request from "./request";
export function scoreApi(payload) {
    return request.post("/score", payload);
}
export function batchScoreApi(payload) {
    return request.post("/score/batch", payload);
}
