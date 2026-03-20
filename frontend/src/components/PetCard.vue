<script setup lang="ts">
import type { StudentPet } from "@/types";
import { computed } from "vue";
import { nextLevelNeed } from "@/utils/petLevel";
import AppIcon from "@/components/AppIcon.vue";

const props = defineProps<{
  item: StudentPet;
}>();

const progress = computed(() => {
  if (!props.item.level || props.item.exp === null) return 0;
  const need = nextLevelNeed(props.item.level);
  if (!need) return 100;
  return Math.max(0, Math.min(100, Math.round((props.item.exp / need) * 100)));
});

const petImage = computed(() => `/pets/${props.item.imageKey || "cat_1"}.svg`);
const remainToNextLevel = computed(() => {
  if (!props.item.level || props.item.exp === null) return 10;
  const need = nextLevelNeed(props.item.level);
  if (!need) return 0;
  return Math.max(0, need - props.item.exp);
});
const adopted = computed(() => Boolean(props.item.petId));
</script>

<template>
  <div class="app-card overflow-hidden p-4 transition hover:-translate-y-1 hover:shadow-float">
    <div class="flex items-center justify-between gap-2">
      <span class="app-chip-primary">Lv.{{ item.level || 1 }}</span>
      <span class="app-chip-gold">
        <AppIcon name="coin" tone="gold" :size="14" />
        {{ item.coins ?? 0 }}
      </span>
    </div>

    <div class="mt-4 flex justify-center">
      <div class="pet-card-art flex h-32 w-32 items-center justify-center rounded-[2rem] border border-white/70">
        <img :src="petImage" class="h-24 w-24 object-contain drop-shadow-sm" alt="pet" />
      </div>
    </div>

    <div class="mt-4 text-center">
      <div class="text-xs font-semibold tracking-[0.2em] text-slate-400">{{ item.studentName }}</div>
      <div class="mt-2 text-2xl font-black text-ink">{{ item.petName || "等待领养" }}</div>
      <div class="mt-2 text-sm text-slate-500">
        {{ adopted ? "点击继续记录行为并培养宠物" : "点击为这位同学领养第一只宠物" }}
      </div>
    </div>

    <div class="mt-5">
      <div class="mb-2 flex items-center justify-between text-xs font-semibold text-slate-500">
        <span>成长进度</span>
        <span v-if="remainToNextLevel">距 Lv.{{ (item.level || 1) + 1 }} 还差 {{ remainToNextLevel }}</span>
        <span v-else>已达到当前最高等级</span>
      </div>
      <el-progress :percentage="progress" :show-text="false" :stroke-width="12" />
      <div class="mt-2 flex items-center justify-between text-xs text-slate-500">
        <span>EXP {{ item.exp ?? 0 }}</span>
        <span>{{ adopted ? "成长中" : "等待孵化" }}</span>
      </div>
    </div>
  </div>
</template>
