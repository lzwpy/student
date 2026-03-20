<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { getRecentOperationsApi, revertOperationApi } from "@/api/operations";
import { useClassroomStore } from "@/stores/classroom";
import type { RecentOperationItem } from "@/types";
import OperationTimeline from "@/components/OperationTimeline.vue";
import AppIcon from "@/components/AppIcon.vue";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const logs = ref<RecentOperationItem[]>([]);
const loading = ref(false);
const query = ref({ startDate: "", endDate: "", limit: 50 });

async function loadData() {
  loading.value = true;
  try {
    logs.value = await getRecentOperationsApi({
      classId: classId.value || undefined,
      startDate: query.value.startDate || undefined,
      endDate: query.value.endDate || undefined,
      limit: query.value.limit
    });
  } finally {
    loading.value = false;
  }
}

async function revertItem(id: number) {
  await revertOperationApi(id);
  ElMessage.success("撤回成功");
  await loadData();
}

onMounted(loadData);
watch(classId, () => {
  void loadData();
});
</script>

<template>
  <div class="space-y-4">
    <div class="app-card p-6">
      <div class="flex flex-col gap-5 lg:flex-row lg:items-end lg:justify-between">
        <div>
          <div class="flex items-center gap-3">
            <AppIcon name="history" tone="mint" :size="20" />
            <h3 class="text-2xl font-black text-ink">操作日志</h3>
          </div>
          <p class="mt-3 text-sm leading-6 text-slate-500">
            这里展示完整的最近操作时间线，包含打分与购买记录，并支持对尚未处理过的操作执行撤回。
          </p>
        </div>
        <div class="flex flex-wrap gap-3">
          <el-date-picker v-model="query.startDate" value-format="YYYY-MM-DD" type="date" placeholder="开始日期" />
          <el-date-picker v-model="query.endDate" value-format="YYYY-MM-DD" type="date" placeholder="结束日期" />
          <el-select v-model="query.limit" class="w-32">
            <el-option :value="20" label="20 条" />
            <el-option :value="50" label="50 条" />
            <el-option :value="100" label="100 条" />
          </el-select>
          <el-button type="primary" @click="loadData">查询</el-button>
        </div>
      </div>
    </div>

    <OperationTimeline
      :items="logs"
      :loading="loading"
      empty-text="当前筛选条件下还没有操作记录"
      @revert="revertItem"
    />
  </div>
</template>
