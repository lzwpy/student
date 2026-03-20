<script setup lang="ts">
import AppIcon from "@/components/AppIcon.vue";
</script>

<template>
  <div class="app-card border border-rosey-pale p-6">
    <div class="flex items-center gap-3">
      <AppIcon name="warning" tone="rose" :size="18" />
      <h3 class="text-xl font-black text-rosey-dark">危险操作区</h3>
    </div>
    <p class="mt-3 text-sm leading-7 text-slate-600">
      可扩展：重置班级分值、清空日志、删除班级等高风险操作。当前版本仅保留占位提醒，避免误操作。
    </p>
    <el-alert class="mt-4" type="warning" :closable="false" title="请在确认数据备份后再开放危险操作。" />
  </div>
</template>
