import { defineStore } from "pinia";
import { loginApi } from "@/api/auth";

interface AuthState {
  token: string;
  nickname: string;
  username: string;
  teacherId: number | null;
}

export const useAuthStore = defineStore("auth", {
  state: (): AuthState => ({
    token: localStorage.getItem("token") || "",
    nickname: localStorage.getItem("nickname") || "",
    username: localStorage.getItem("username") || "",
    teacherId: Number(localStorage.getItem("teacherId") || 0) || null
  }),
  getters: {
    isLogin: (state) => Boolean(state.token)
  },
  actions: {
    async login(username: string, password: string) {
      const data = await loginApi({ username, password });
      this.token = data.token;
      this.nickname = data.nickname;
      this.username = data.username;
      this.teacherId = data.teacherId;
      localStorage.setItem("token", data.token);
      localStorage.setItem("nickname", data.nickname || "");
      localStorage.setItem("username", data.username || "");
      localStorage.setItem("teacherId", String(data.teacherId || ""));
    },
    logout() {
      this.token = "";
      this.nickname = "";
      this.username = "";
      this.teacherId = null;
      localStorage.removeItem("token");
      localStorage.removeItem("nickname");
      localStorage.removeItem("username");
      localStorage.removeItem("teacherId");
    }
  }
});
