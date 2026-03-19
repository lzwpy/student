<script setup lang="ts">
import type { LeaderboardItem } from "@/types";

defineProps<{
  items: LeaderboardItem[];
}>();
</script>

<template>
  <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
    <div v-for="(item, idx) in items.slice(0, 3)" :key="item.studentId" class="page-card p-4 text-center">
      <div class="text-2xl mb-2">NO.{{ idx + 1 }}</div>
      <img :src="`/pets/${item.imageKey || 'cat_1'}.svg`" class="h-16 w-16 mx-auto" alt="pet" />
      <div class="font-semibold mt-2">{{ item.studentName }}</div>
      <div class="text-slate-500 text-sm">{{ item.petName }}</div>
      <div class="mt-2 text-sm">Lv.{{ item.level }}</div>
    </div>
  </div>
</template>
