import request from "./request";
import type { Classroom } from "@/types";

export function getClassroomsApi() {
  return request.get<unknown, Classroom[]>("/classrooms");
}

export function createClassroomApi(payload: { name: string }) {
  return request.post("/classrooms", payload);
}

export function renameClassroomApi(id: number, payload: { name: string }) {
  return request.put(`/classrooms/${id}`, payload);
}
