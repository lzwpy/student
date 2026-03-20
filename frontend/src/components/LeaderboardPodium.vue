<script setup lang="ts">
import type { LeaderboardItem } from "@/types";
import AppIcon from "@/components/AppIcon.vue";

defineProps<{
  items: LeaderboardItem[];
}>();
</script>

<template>
  <div class="grid grid-cols-1 gap-4 md:grid-cols-3">
    <div
      v-for="(item, idx) in items.slice(0, 3)"
      :key="item.studentId"
      class="app-card p-5 text-center"
      :class="idx === 0 ? 'md:-translate-y-4' : idx === 1 ? 'md:translate-y-6' : 'md:translate-y-10'"
    >
      <div class="flex justify-center">
        <span class="app-chip-gold">NO.{{ idx + 1 }}</span>
      </div>
      <div class="mt-4 flex justify-center">
        <div class="pet-card-art flex h-24 w-24 items-center justify-center rounded-[2rem] border border-white/70">
          <img :src="`/pets/${item.imageKey || 'cat_1'}.svg`" class="h-16 w-16 object-contain" alt="pet" />
        </div>
      </div>
      <div class="mt-4 text-2xl font-black text-ink">{{ item.studentName }}</div>
      <div class="mt-1 text-sm text-slate-500">{{ item.petName || "未领养" }}</div>
      <div class="mt-3 flex flex-wrap justify-center gap-2">
        <span class="app-chip-primary">{{ item.level === null ? "未领养宠物" : `Lv.${item.level}` }}</span>
        <span class="app-chip-gold">
          <AppIcon name="coin" tone="gold" :size="14" />
          {{ item.coins }}
        </span>
      </div>
    </div>
  </div>
</template>
