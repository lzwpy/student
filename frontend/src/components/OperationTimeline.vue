<script setup lang="ts">
import type { RecentOperationItem } from "@/types";
import AppIcon from "@/components/AppIcon.vue";

const props = withDefaults(defineProps<{
  items: RecentOperationItem[];
  loading?: boolean;
  emptyText?: string;
}>(), {
  loading: false,
  emptyText: "还没有操作记录"
});

const emit = defineEmits<{
  revert: [id: number];
}>();

function formatTime(value: string) {
  return new Date(value).toLocaleString("zh-CN", {
    hour12: false
  });
}

function actionLabel(type: RecentOperationItem["actionType"]) {
  return type === "SCORE" ? "打分" : "购买";
}

function actionTone(type: RecentOperationItem["actionType"]) {
  return type === "SCORE" ? "mint" : "gold";
}

function actionIcon(type: RecentOperationItem["actionType"]) {
  return type === "SCORE" ? "spark" : "gift";
}

function signedValue(value: number, label: string) {
  if (value > 0) return `${label} +${value}`;
  return `${label} ${value}`;
}
</script>

<template>
  <div class="space-y-4">
    <div v-if="loading" class="app-card-soft p-5 text-sm text-slate-500">
      正在加载最近操作记录...
    </div>

    <div
      v-else-if="!props.items.length"
      class="app-card-soft flex flex-col items-center justify-center gap-3 p-8 text-center text-slate-500"
    >
      <AppIcon name="history" tone="slate" :size="22" />
      <span>{{ emptyText }}</span>
    </div>

    <template v-else>
      <article
        v-for="item in props.items"
        :key="item.id"
        class="app-card-soft p-5 transition"
        :class="{ 'opacity-60': item.reverted }"
      >
        <div class="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
          <div class="flex min-w-0 gap-4">
            <AppIcon :name="actionIcon(item.actionType)" :tone="actionTone(item.actionType)" :size="22" />
            <div class="min-w-0">
              <div class="flex flex-wrap items-center gap-2">
                <span class="app-tag" :class="item.actionType === 'SCORE' ? 'app-tag-primary' : 'app-tag-gold'">
                  {{ actionLabel(item.actionType) }}
                </span>
                <span class="truncate text-lg font-bold text-ink">{{ item.title }}</span>
                <span v-if="item.reverted" class="app-tag app-tag-danger">已撤回</span>
              </div>
              <div class="mt-2 flex flex-wrap items-center gap-x-3 gap-y-1 text-sm text-slate-500">
                <span>{{ item.studentName }}</span>
                <span>{{ formatTime(item.createdAt) }}</span>
                <span>{{ item.summary }}</span>
              </div>
            </div>
          </div>

          <div class="flex flex-wrap items-center gap-2 lg:justify-end">
            <span
              v-if="item.expChange !== 0"
              class="app-chip"
              :class="item.expChange > 0 ? 'app-chip-primary' : 'app-chip-danger'"
            >
              {{ signedValue(item.expChange, "EXP") }}
            </span>
            <span
              v-if="item.coinChange !== 0"
              class="app-chip"
              :class="item.coinChange > 0 ? 'app-chip-gold' : 'app-chip-danger'"
            >
              {{ signedValue(item.coinChange, "金币") }}
            </span>
            <el-button
              v-if="item.revertible"
              type="danger"
              plain
              @click="emit('revert', item.id)"
            >
              撤回
            </el-button>
          </div>
        </div>
      </article>
    </template>
  </div>
</template>
