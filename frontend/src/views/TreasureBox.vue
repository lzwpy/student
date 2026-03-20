<script setup lang="ts">
import AppIcon from "@/components/AppIcon.vue";
</script>

<template>
  <div class="app-card p-10 text-center">
    <div class="flex justify-center">
      <AppIcon name="gift" tone="gold" :size="28" />
    </div>
    <h2 class="mt-4 text-3xl font-black text-ink">百宝箱</h2>
    <p class="mx-auto mt-3 max-w-2xl text-sm leading-7 text-slate-500">
      这里预留给勋章、道具兑换、奖励记录等后续扩展能力，先统一成与其它模块一致的卡通风格容器。
    </p>
  </div>
</template>
