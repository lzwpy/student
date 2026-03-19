<script setup lang="ts">
import { ref } from "vue";
import { changePasswordApi } from "@/api/auth";
import { ElMessage } from "element-plus";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();
const form = ref({ oldPassword: "", newPassword: "" });
const loading = ref(false);

async function submit() {
  loading.value = true;
  try {
    await changePasswordApi(form.value);
    ElMessage.success("密码修改成功");
    form.value.oldPassword = "";
    form.value.newPassword = "";
  } catch (error: any) {
    ElMessage.error(error?.message || "修改失败");
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="page-card p-6 space-y-4">
    <h3 class="text-xl font-semibold">账号管理</h3>
    <div class="text-slate-600 text-sm">当前账号：{{ authStore.username }}（{{ authStore.nickname }}）</div>
    <el-form label-width="90px" class="max-w-xl">
      <el-form-item label="旧密码">
        <el-input v-model="form.oldPassword" type="password" show-password />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="form.newPassword" type="password" show-password />
      </el-form-item>
      <el-button type="primary" :loading="loading" @click="submit">保存</el-button>
    </el-form>
  </div>
</template>
