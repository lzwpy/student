import request from "./request";

export function adoptPetApi(studentId: number, payload: { petName: string; imageKey: string }) {
  return request.post(`/students/${studentId}/pet`, payload);
}

export function getPetApi(studentId: number) {
  return request.get(`/students/${studentId}/pet`);
}
