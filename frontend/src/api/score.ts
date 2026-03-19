import request from "./request";

export function scoreApi(payload: { studentId: number; ruleId: number }) {
  return request.post("/score", payload);
}

export function batchScoreApi(payload: { studentIds: number[]; ruleId: number }) {
  return request.post("/score/batch", payload);
}
