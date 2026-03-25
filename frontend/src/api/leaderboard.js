import request from "./request";
export function getPetRankApi(classId) {
    return request.get(`/leaderboard/pet?classId=${classId}`);
}
export function getCoinRankApi(classId) {
    return request.get(`/leaderboard/coin?classId=${classId}`);
}
