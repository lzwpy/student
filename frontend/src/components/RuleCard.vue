<script setup lang="ts">
import type { RuleItem } from "@/types";
defineProps<{ rule: RuleItem }>();
const emit = defineEmits<{ edit: []; remove: [] }>();
</script>

<template>
  <div class="page-card p-4">
    <div class="font-semibold truncate">{{ rule.name }}</div>
    <div class="text-sm text-slate-500 mt-2">EXP {{ rule.expValue }} ｜ 金币 {{ rule.coinValue }}</div>
    <div class="text-xs mt-1" :class="rule.type === 'positive' ? 'text-emerald-600' : 'text-rose-600'">
      {{ rule.type === "positive" ? "积极行为" : "需要改进" }}
    </div>
    <div class="mt-3 flex gap-2">
      <el-button size="small" @click="emit('edit')">编辑</el-button>
      <el-button size="small" type="danger" plain @click="emit('remove')">删除</el-button>
    </div>
  </div>
</template>
