<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useAuthStore } from "@/stores/auth";
import { useClassroomStore } from "@/stores/classroom";
import ClassSelector from "@/components/ClassSelector.vue";
import AppIcon from "@/components/AppIcon.vue";

const authStore = useAuthStore();
const classroomStore = useClassroomStore();
const route = useRoute();

const navItems = [
  { to: "/pet-garden", label: "宠物乐园", icon: "pet" as const, activePrefix: "/pet-garden" },
  { to: "/shop", label: "小卖部", icon: "shop" as const, activePrefix: "/shop" },
  { to: "/leaderboard", label: "光荣榜", icon: "trophy" as const, activePrefix: "/leaderboard" },
  { to: "/treasure-box", label: "百宝箱", icon: "gift" as const, activePrefix: "/treasure-box" },
  { to: "/settings/account", label: "系统设置", icon: "settings" as const, activePrefix: "/settings" }
];

const pageTitle = computed(() => {
  return navItems.find((item) => route.path.startsWith(item.activePrefix))?.label ?? "积分养宠物";
});

onMounted(async () => {
  await classroomStore.loadClassrooms();
});
</script>

<template>
  <div class="app-shell">
    <header class="px-4 pt-5">
      <div class="mx-auto flex max-w-7xl flex-col gap-4">
        <div class="app-card flex flex-col gap-4 px-5 py-4 lg:flex-row lg:items-center lg:justify-between">
          <div class="flex items-center gap-4">
            <div class="pet-card-art flex h-16 w-16 items-center justify-center rounded-3xl border border-line">
              <AppIcon name="pet" tone="gold" :size="26" />
            </div>
            <div>
              <router-link to="/" class="text-2xl font-black tracking-wide text-ink">积分养宠物</router-link>
              <p class="mt-1 text-sm text-slate-500">把课堂管理做成更有趣的宠物养成体验</p>
            </div>
          </div>

          <div class="flex flex-col gap-3 lg:items-end">
            <ClassSelector />
            <div class="flex items-center gap-3">
              <span class="app-chip-gold text-sm">当前页面：{{ pageTitle }}</span>
              <el-button size="small" @click="authStore.logout()">退出</el-button>
            </div>
          </div>
        </div>

        <nav class="app-card-soft flex flex-wrap items-center gap-3 px-4 py-3">
          <router-link
            v-for="item in navItems"
            :key="item.to"
            :to="item.to"
            class="inline-flex items-center gap-3 rounded-full px-4 py-3 text-sm font-semibold transition"
            :class="route.path.startsWith(item.activePrefix) ? 'bg-slate-900 text-white shadow-soft' : 'bg-white text-slate-600 border border-line'"
          >
            <AppIcon :name="item.icon" :tone="route.path.startsWith(item.activePrefix) ? 'dark' : 'slate'" :size="16" />
            <span>{{ item.label }}</span>
          </router-link>
        </nav>
      </div>
    </header>

    <main class="mx-auto max-w-7xl px-4 pb-8 pt-5">
      <router-view />
    </main>
  </div>
</template>
