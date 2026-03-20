<script setup lang="ts">
import type { RuleItem } from "@/types";
import AppIcon from "@/components/AppIcon.vue";

defineProps<{ rule: RuleItem }>();
const emit = defineEmits<{ edit: []; remove: [] }>();
</script>

<template>
  <div class="app-card p-4">
    <div class="flex items-start justify-between gap-3">
      <div class="flex items-center gap-3">
        <AppIcon :name="rule.type === 'positive' ? 'spark' : 'warning'" :tone="rule.type === 'positive' ? 'mint' : 'rose'" :size="18" />
        <div>
          <div class="truncate text-lg font-bold text-ink">{{ rule.name }}</div>
          <div class="mt-1 text-xs" :class="rule.type === 'positive' ? 'text-mint-dark' : 'text-rosey-dark'">
            {{ rule.type === "positive" ? "积极行为" : "需要改进" }}
          </div>
        </div>
      </div>
      <span class="app-tag" :class="rule.type === 'positive' ? 'app-tag-primary' : 'app-tag-danger'">
        {{ rule.icon }}
      </span>
    </div>

    <div class="mt-4 flex flex-wrap gap-2">
      <span class="app-chip" :class="rule.expValue >= 0 ? 'app-chip-primary' : 'app-chip-danger'">EXP {{ rule.expValue }}</span>
      <span class="app-chip" :class="rule.coinValue >= 0 ? 'app-chip-gold' : 'app-chip-danger'">金币 {{ rule.coinValue }}</span>
    </div>

    <div class="mt-4 flex gap-2">
      <el-button size="small" @click="emit('edit')">编辑</el-button>
      <el-button size="small" type="danger" plain @click="emit('remove')">删除</el-button>
    </div>
  </div>
</template>
