import request from "./request";
import type { ShopItem } from "@/types";

export function getShopItemsApi() {
  return request.get<unknown, ShopItem[]>("/shop/items");
}

export function createShopItemApi(payload: Omit<ShopItem, "id" | "teacherId">) {
  return request.post("/shop/items", payload);
}

export function updateShopItemApi(id: number, payload: Omit<ShopItem, "id" | "teacherId">) {
  return request.put(`/shop/items/${id}`, payload);
}

export function deleteShopItemApi(id: number) {
  return request.delete(`/shop/items/${id}`);
}

export function purchaseApi(payload: { studentId: number; itemId: number }) {
  return request.post("/shop/purchase", payload);
}
