<script setup lang="ts">
import { useAuthStore } from "@/stores/auth";
import { onMounted } from "vue";
import { useClassroomStore } from "@/stores/classroom";
import ClassSelector from "@/components/ClassSelector.vue";

const authStore = useAuthStore();
const classroomStore = useClassroomStore();

onMounted(async () => {
  await classroomStore.loadClassrooms();
});
</script>

<template>
  <div class="min-h-screen">
    <header class="bg-white border-b border-slate-200">
      <div class="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
        <div class="flex items-center gap-4">
          <router-link class="font-bold text-slate-800" to="/">积分养宠物</router-link>
          <router-link to="/pet-garden">宠物乐园</router-link>
          <router-link to="/shop">小卖部</router-link>
          <router-link to="/leaderboard">光荣榜</router-link>
          <router-link to="/treasure-box">百宝箱</router-link>
          <router-link to="/settings/account">系统设置</router-link>
        </div>
        <div class="flex items-center gap-3">
          <ClassSelector />
          <el-button size="small" @click="authStore.logout()">退出</el-button>
        </div>
      </div>
    </header>
    <main class="max-w-7xl mx-auto p-4">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
a.router-link-active {
  color: #2563eb;
  font-weight: 600;
}
</style>
