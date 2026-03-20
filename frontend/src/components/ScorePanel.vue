<script setup lang="ts">
import type { RuleItem, StudentPet } from "@/types";
import { computed, ref } from "vue";
import AppIcon from "@/components/AppIcon.vue";
import RecentOperationsDrawer from "@/components/RecentOperationsDrawer.vue";

const props = defineProps<{
  visible: boolean;
  student?: StudentPet | null;
  rules: RuleItem[];
  reloadKey?: number;
}>();

const emit = defineEmits<{
  "update:visible": [value: boolean];
  score: [ruleId: number];
  reverted: [];
}>();

const positiveRules = computed(() => props.rules.filter((r) => r.type === "positive"));
const negativeRules = computed(() => props.rules.filter((r) => r.type === "negative"));
const operationVisible = ref(false);
const petImage = computed(() => `/pets/${props.student?.imageKey || "cat_1"}.svg`);

function ruleIcon(icon?: string) {
  const map: Record<string, "star" | "spark" | "history" | "warning" | "pet"> = {
    hand: "spark",
    heart: "pet",
    clock: "history",
    mute: "warning",
    mic: "spark",
    star: "star"
  };
  return map[icon || "star"] || "star";
}
</script>

<template>
  <el-dialog
    :model-value="visible"
    width="1020px"
    title="行为打分"
    @close="emit('update:visible', false)"
  >
    <div v-if="student" class="space-y-5">
      <section class="app-card-soft flex flex-col gap-4 p-5 md:flex-row md:items-center md:justify-between">
        <div class="flex items-center gap-4">
          <div class="pet-card-art flex h-24 w-24 items-center justify-center rounded-[2rem] border border-white/70">
            <img :src="petImage" class="h-16 w-16 object-contain" alt="pet" />
          </div>
          <div>
            <div class="text-3xl font-black text-ink">{{ student.studentName }}</div>
            <div class="mt-2 text-base font-semibold text-slate-500">{{ student.petName || "未领养" }}</div>
            <div class="mt-3 flex flex-wrap gap-2">
              <span class="app-chip-primary">Lv.{{ student.level || 1 }}</span>
              <span class="app-chip-gold">
                <AppIcon name="coin" tone="gold" :size="14" />
                {{ student.coins ?? 0 }}
              </span>
            </div>
          </div>
        </div>

        <div class="flex flex-wrap gap-3">
          <el-button @click="operationVisible = true">
            <AppIcon name="history" tone="slate" :size="14" />
            最近操作记录
          </el-button>
        </div>
      </section>

      <div class="grid grid-cols-1 gap-4 md:grid-cols-2">
        <div class="app-card p-5">
          <div class="mb-4 flex items-center gap-3">
            <AppIcon name="spark" tone="mint" :size="18" />
            <h3 class="text-xl font-bold text-mint-dark">积极行为</h3>
          </div>
          <div class="space-y-3">
          <button
            v-for="rule in positiveRules"
            :key="rule.id"
            class="w-full rounded-[1.5rem] border border-mint-soft bg-[#f9fffd] p-4 text-left transition hover:-translate-y-0.5 hover:shadow-soft"
            @click="emit('score', rule.id)"
          >
            <div class="flex items-start justify-between gap-4">
              <div class="flex min-w-0 gap-3">
                <AppIcon :name="ruleIcon(rule.icon)" tone="mint" :size="18" />
                <div class="min-w-0">
                  <div class="font-bold text-ink">{{ rule.name }}</div>
                  <div class="mt-1 text-xs text-slate-500">轻触即可记录一次积极表现</div>
                </div>
              </div>
              <div class="flex flex-wrap justify-end gap-2">
                <span class="app-chip-primary">EXP +{{ Math.abs(rule.expValue) }}</span>
                <span class="app-chip-gold">金币 +{{ Math.abs(rule.coinValue) }}</span>
              </div>
            </div>
          </button>
          </div>
        </div>

        <div class="app-card p-5">
          <div class="mb-4 flex items-center gap-3">
            <AppIcon name="warning" tone="rose" :size="18" />
            <h3 class="text-xl font-bold text-rosey-dark">需要改进</h3>
          </div>
          <div class="space-y-3">
          <button
            v-for="rule in negativeRules"
            :key="rule.id"
            class="w-full rounded-[1.5rem] border border-rosey-pale bg-[#fffafb] p-4 text-left transition hover:-translate-y-0.5 hover:shadow-soft"
            @click="emit('score', rule.id)"
          >
            <div class="flex items-start justify-between gap-4">
              <div class="flex min-w-0 gap-3">
                <AppIcon :name="ruleIcon(rule.icon)" tone="rose" :size="18" />
                <div class="min-w-0">
                  <div class="font-bold text-ink">{{ rule.name }}</div>
                  <div class="mt-1 text-xs text-slate-500">需要及时记录并支持后续撤回</div>
                </div>
              </div>
              <div class="flex flex-wrap justify-end gap-2">
                <span class="app-chip-danger">EXP {{ rule.expValue }}</span>
                <span class="app-chip-danger">金币 {{ rule.coinValue }}</span>
              </div>
            </div>
          </button>
          </div>
        </div>
      </div>
    </div>

    <RecentOperationsDrawer
      v-model:visible="operationVisible"
      :student-id="student?.studentId"
      :reload-key="reloadKey"
      :title="`${student?.studentName || ''}的最近操作`"
      @reverted="emit('reverted')"
    />
  </el-dialog>
</template>
