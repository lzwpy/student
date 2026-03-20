<script setup lang="ts">
import { useRoute } from "vue-router";
import AppIcon from "@/components/AppIcon.vue";

const route = useRoute();

const menuItems = [
  { to: "/settings/account", label: "账号管理", icon: "student" as const },
  { to: "/settings/class-students", label: "班级学生", icon: "pet" as const },
  { to: "/settings/rules", label: "分值管理", icon: "star" as const },
  { to: "/settings/logs", label: "操作日志", icon: "history" as const },
  { to: "/settings/danger", label: "危险操作区", icon: "warning" as const }
];
</script>

<template>
  <div class="grid grid-cols-12 gap-5">
    <aside class="col-span-12 md:col-span-4 lg:col-span-3 xl:col-span-2">
      <div class="app-card p-4">
        <div class="mb-4">
          <p class="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">Settings</p>
          <h3 class="mt-2 text-xl font-black text-ink">系统设置</h3>
        </div>
        <div class="space-y-2">
          <router-link
            v-for="item in menuItems"
            :key="item.to"
            :to="item.to"
            class="flex items-center gap-3 rounded-2xl px-3 py-3 text-sm font-semibold transition"
            :class="route.path === item.to
              ? 'bg-slate-900 text-white shadow-soft'
              : item.to.endsWith('/danger')
                ? 'text-rosey-dark hover:bg-rosey-soft'
                : 'text-slate-600 hover:bg-orange-50'"
          >
            <AppIcon
              :name="item.icon"
              :tone="route.path === item.to ? 'dark' : item.to.endsWith('/danger') ? 'rose' : 'slate'"
              :size="16"
            />
            <span>{{ item.label }}</span>
          </router-link>
        </div>
      </div>
    </aside>
    <section class="col-span-12 md:col-span-8 lg:col-span-9 xl:col-span-10">
      <router-view />
    </section>
  </div>
</template>
