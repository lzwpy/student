<script setup lang="ts">
import { onMounted, ref } from "vue";
import type { RuleItem } from "@/types";
import { createRuleApi, deleteRuleApi, getRulesApi, updateRuleApi } from "@/api/rule";
import RuleCard from "@/components/RuleCard.vue";
import { ElMessage } from "element-plus";
import AppIcon from "@/components/AppIcon.vue";

const rules = ref<RuleItem[]>([]);
const dialogVisible = ref(false);
const editingId = ref<number | null>(null);
const form = ref({
  name: "",
  type: "positive" as "positive" | "negative",
  icon: "star",
  expValue: 1,
  coinValue: 1,
  sortOrder: 1
});

async function loadData() {
  rules.value = await getRulesApi();
}

function openCreate() {
  editingId.value = null;
  form.value = { name: "", type: "positive", icon: "star", expValue: 1, coinValue: 1, sortOrder: 1 };
  dialogVisible.value = true;
}

function openEdit(item: RuleItem) {
  editingId.value = item.id;
  form.value = { ...item };
  dialogVisible.value = true;
}

async function submit() {
  if (editingId.value) {
    await updateRuleApi(editingId.value, form.value);
  } else {
    await createRuleApi(form.value);
  }
  ElMessage.success("保存成功");
  dialogVisible.value = false;
  await loadData();
}

async function remove(id: number) {
  await deleteRuleApi(id);
  ElMessage.success("删除成功");
  await loadData();
}

onMounted(loadData);
</script>

<template>
  <div class="space-y-4">
    <div class="app-section-header">
      <div class="flex items-center gap-3">
        <AppIcon name="star" tone="mint" :size="18" />
        <h3 class="text-xl font-black text-ink">分值管理</h3>
      </div>
      <el-button type="primary" @click="openCreate">新建规则</el-button>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <RuleCard
        v-for="item in rules"
        :key="item.id"
        :rule="item"
        @edit="openEdit(item)"
        @remove="remove(item.id)"
      />
    </div>
  </div>

  <el-dialog v-model="dialogVisible" title="规则" width="500px">
    <el-form label-width="90px">
      <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="类型">
        <el-radio-group v-model="form.type">
          <el-radio label="positive">积极行为</el-radio>
          <el-radio label="negative">需要改进</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="经验"><el-input-number v-model="form.expValue" /></el-form-item>
      <el-form-item label="金币"><el-input-number v-model="form.coinValue" /></el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submit">保存</el-button>
    </template>
  </el-dialog>
</template>
