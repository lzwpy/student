import request from "./request";
export function getRecentOperationsApi(params) {
    const query = new URLSearchParams();
    if (params.classId)
        query.set("classId", String(params.classId));
    if (params.studentId)
        query.set("studentId", String(params.studentId));
    if (params.limit)
        query.set("limit", String(params.limit));
    if (params.startDate)
        query.set("startDate", params.startDate);
    if (params.endDate)
        query.set("endDate", params.endDate);
    return request.get(`/operations/recent?${query.toString()}`);
}
export function revertOperationApi(id) {
    return request.post(`/operations/${id}/revert`);
}
