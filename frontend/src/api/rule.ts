import request from "./request";
import type { RuleItem } from "@/types";

export function getRulesApi() {
  return request.get<unknown, RuleItem[]>("/rules");
}

export function createRuleApi(payload: Omit<RuleItem, "id" | "teacherId">) {
  return request.post("/rules", payload);
}

export function updateRuleApi(id: number, payload: Omit<RuleItem, "id" | "teacherId">) {
  return request.put(`/rules/${id}`, payload);
}

export function deleteRuleApi(id: number) {
  return request.delete(`/rules/${id}`);
}
