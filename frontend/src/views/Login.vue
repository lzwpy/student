<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";
import { ElMessage } from "element-plus";

const router = useRouter();
const authStore = useAuthStore();
const form = ref({ username: "", password: "" });
const loading = ref(false);

async function submit() {
  try {
    loading.value = true;
    await authStore.login(form.value.username, form.value.password);
    ElMessage.success("登录成功");
    router.push("/");
  } catch (error: any) {
    ElMessage.error(error?.message || "登录失败");
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-slate-100">
    <div class="page-card w-[420px] p-8">
      <h1 class="text-2xl font-bold mb-6 text-center">教师登录</h1>
      <el-form @submit.prevent="submit">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-button type="primary" class="w-full" :loading="loading" @click="submit">登录</el-button>
      </el-form>
    </div>
  </div>
</template>
