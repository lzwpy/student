<script setup lang="ts">
import type { ShopItem } from "@/types";
import AppIcon from "@/components/AppIcon.vue";

defineProps<{ item: ShopItem }>();
const emit = defineEmits<{ buy: []; edit: []; remove: [] }>();
</script>

<template>
  <div class="app-card overflow-hidden p-4 transition hover:-translate-y-1 hover:shadow-float">
    <div class="rounded-[1.75rem] border border-white/70 bg-gradient-to-br from-[#fff8d9] via-[#ffe8b3] to-[#ffd7a3] p-5">
      <div class="flex items-start justify-between gap-3">
        <AppIcon name="gift" tone="gold" :size="22" />
        <span class="app-tag-gold">{{ item.stock < 0 ? "库存无限" : `库存 ${item.stock}` }}</span>
      </div>
      <div class="mt-8 text-3xl font-black text-gold-dark">{{ item.name }}</div>
      <div class="mt-2 text-sm leading-6 text-amber-900/75">{{ item.description || "给课堂奖励准备的小礼物" }}</div>
    </div>

    <div class="mt-4 flex items-center justify-between gap-3">
      <span class="app-chip-gold">
        <AppIcon name="coin" tone="gold" :size="14" />
        {{ item.price }} 金币
      </span>
      <span class="app-tag" :class="item.status === 1 ? 'app-tag-primary' : 'app-tag-danger'">
        {{ item.status === 1 ? "可购买" : "已下架" }}
      </span>
    </div>

    <div class="mt-4 flex gap-2">
      <el-button size="small" type="primary" @click="emit('buy')">购买</el-button>
      <el-button size="small" @click="emit('edit')">
        <AppIcon name="edit" tone="slate" :size="14" />
        编辑
      </el-button>
      <el-button size="small" type="danger" plain @click="emit('remove')">
        <AppIcon name="remove" tone="rose" :size="14" />
        下架
      </el-button>
    </div>
  </div>
</template>
