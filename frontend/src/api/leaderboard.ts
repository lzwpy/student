import request from "./request";
import type { LeaderboardItem } from "@/types";

export function getPetRankApi(classId: number) {
  return request.get<unknown, LeaderboardItem[]>(`/leaderboard/pet?classId=${classId}`);
}

export function getCoinRankApi(classId: number) {
  return request.get<unknown, LeaderboardItem[]>(`/leaderboard/coin?classId=${classId}`);
}
