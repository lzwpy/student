import request from "./request";

export interface ScoreLog {
  id: number;
  studentId: number;
  classroomId: number;
  ruleId: number;
  ruleName: string;
  expChange: number;
  coinChange: number;
  operatorId: number;
  createdAt: string;
}

export function getLogsApi(params: { classId?: number; startDate?: string; endDate?: string }) {
  const query = new URLSearchParams();
  if (params.classId) query.set("classId", String(params.classId));
  if (params.startDate) query.set("startDate", params.startDate);
  if (params.endDate) query.set("endDate", params.endDate);
  return request.get<unknown, ScoreLog[]>(`/logs?${query.toString()}`);
}
