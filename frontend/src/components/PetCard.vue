<script setup lang="ts">
import type { StudentPet } from "@/types";
import { computed } from "vue";
import { nextLevelNeed } from "@/utils/petLevel";

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
</script>

<template>
  <div class="page-card p-4 hover:shadow-md transition">
    <div class="flex items-center justify-between">
      <el-tag size="small" type="success">Lv.{{ item.level || 1 }}</el-tag>
      <span class="text-xs text-slate-500">金币 {{ item.coins ?? 0 }}</span>
    </div>
    <div class="flex justify-center py-3">
      <img :src="petImage" class="h-20 w-20 object-contain" alt="pet" />
    </div>
    <div class="text-center">
      <div class="text-slate-500 text-xs">{{ item.studentName }}</div>
      <div class="font-semibold mt-1">{{ item.petName || "未领养" }}</div>
    </div>
    <div class="mt-3">
      <el-progress :percentage="progress" :show-text="false" :stroke-width="8" />
      <div class="text-xs text-slate-500 mt-1">EXP {{ item.exp ?? 0 }}</div>
    </div>
  </div>
</template>
