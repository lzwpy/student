<script setup lang="ts">
import type { ShopItem } from "@/types";

defineProps<{ item: ShopItem }>();
const emit = defineEmits<{ buy: []; edit: []; remove: [] }>();
</script>

<template>
  <div class="page-card p-4">
    <div class="font-semibold">{{ item.name }}</div>
    <div class="text-slate-500 text-sm mt-1 min-h-10">{{ item.description || "暂无描述" }}</div>
    <div class="mt-2 text-sm">价格：{{ item.price }} 金币</div>
    <div class="text-xs text-slate-500 mt-1">库存：{{ item.stock < 0 ? "无限" : item.stock }}</div>
    <div class="mt-3 flex gap-2">
      <el-button size="small" type="primary" @click="emit('buy')">购买</el-button>
      <el-button size="small" @click="emit('edit')">编辑</el-button>
      <el-button size="small" type="danger" plain @click="emit('remove')">下架</el-button>
    </div>
  </div>
</template>
