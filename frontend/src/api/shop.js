import request from "./request";
export function getShopItemsApi() {
    return request.get("/shop/items");
}
export function createShopItemApi(payload) {
    return request.post("/shop/items", payload);
}
export function updateShopItemApi(id, payload) {
    return request.put(`/shop/items/${id}`, payload);
}
export function deleteShopItemApi(id) {
    return request.delete(`/shop/items/${id}`);
}
export function purchaseApi(payload) {
    return request.post("/shop/purchase", payload);
}
