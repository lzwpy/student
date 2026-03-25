import request from "./request";
import type { RecentOperationItem } from "@/types";

export interface OperationQueryParams {
  classId?: number;
  studentId?: number;
  limit?: number;
  startDate?: string;
  endDate?: string;
}

export function getRecentOperationsApi(params: OperationQueryParams) {
  const query = new URLSearchParams();
  if (params.classId) query.set("classId", String(params.classId));
  if (params.studentId) query.set("studentId", String(params.studentId));
  if (params.limit) query.set("limit", String(params.limit));
  if (params.startDate) query.set("startDate", params.startDate);
  if (params.endDate) query.set("endDate", params.endDate);
  return request.get<unknown, RecentOperationItem[]>(`/operations/recent?${query.toString()}`);
}

export function revertOperationApi(id: number) {
  return request.post<unknown, void>(`/operations/${id}/revert`);
}
