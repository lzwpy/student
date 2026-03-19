import request from "./request";
import type { AuthLoginVO } from "@/types";

export function loginApi(payload: { username: string; password: string }) {
  return request.post<unknown, AuthLoginVO>("/auth/login", payload);
}

export function registerApi(payload: { username: string; password: string; nickname?: string }) {
  return request.post("/auth/register", payload);
}

export function changePasswordApi(payload: { oldPassword: string; newPassword: string }) {
  return request.put("/auth/password", payload);
}
