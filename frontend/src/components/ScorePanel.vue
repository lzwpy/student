<script setup lang="ts">
import type { RuleItem, StudentPet } from "@/types";
import { computed } from "vue";

const props = defineProps<{
  visible: boolean;
  student?: StudentPet | null;
  rules: RuleItem[];
}>();

const emit = defineEmits<{
  "update:visible": [value: boolean];
  score: [ruleId: number];
}>();

const positiveRules = computed(() => props.rules.filter((r) => r.type === "positive"));
const negativeRules = computed(() => props.rules.filter((r) => r.type === "negative"));
</script>

<template>
  <el-dialog
    :model-value="visible"
    width="900px"
    title="行为打分"
    @close="emit('update:visible', false)"
  >
    <div v-if="student" class="mb-4 text-slate-700">
      学生：<b>{{ student.studentName }}</b> ｜ 宠物：<b>{{ student.petName || "未领养" }}</b>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div class="page-card p-4">
        <h3 class="font-semibold text-emerald-600 mb-3">积极行为</h3>
        <div class="space-y-2">
          <button
            v-for="rule in positiveRules"
            :key="rule.id"
            class="w-full text-left p-3 rounded-xl border border-emerald-100 hover:bg-emerald-50"
            @click="emit('score', rule.id)"
          >
            <div class="font-medium">{{ rule.name }}</div>
            <div class="text-xs text-slate-500 mt-1">EXP {{ rule.expValue }} ｜ 金币 {{ rule.coinValue }}</div>
          </button>
        </div>
      </div>
      <div class="page-card p-4">
        <h3 class="font-semibold text-rose-600 mb-3">需要改进</h3>
        <div class="space-y-2">
          <button
            v-for="rule in negativeRules"
            :key="rule.id"
            class="w-full text-left p-3 rounded-xl border border-rose-100 hover:bg-rose-50"
            @click="emit('score', rule.id)"
          >
            <div class="font-medium">{{ rule.name }}</div>
            <div class="text-xs text-slate-500 mt-1">EXP {{ rule.expValue }} ｜ 金币 {{ rule.coinValue }}</div>
          </button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
