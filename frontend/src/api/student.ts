import request from "./request";
import type { StudentPet } from "@/types";

export function getStudentsApi(classId: number) {
  return request.get<unknown, StudentPet[]>(`/classrooms/${classId}/students`);
}

export function createStudentsApi(classId: number, payload: { names: string[] }) {
  return request.post(`/classrooms/${classId}/students`, payload);
}

export function renameStudentApi(id: number, payload: { name: string }) {
  return request.put(`/students/${id}`, payload);
}

export function deleteStudentApi(id: number) {
  return request.delete(`/students/${id}`);
}
