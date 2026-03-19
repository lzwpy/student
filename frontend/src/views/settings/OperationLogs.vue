<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { getLogsApi, type ScoreLog } from "@/api/logs";
import { useClassroomStore } from "@/stores/classroom";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const logs = ref<ScoreLog[]>([]);
const query = ref({ startDate: "", endDate: "" });

async function loadData() {
  logs.value = await getLogsApi({
    classId: classId.value || undefined,
    startDate: query.value.startDate || undefined,
    endDate: query.value.endDate || undefined
  });
}

onMounted(loadData);
</script>

<template>
  <div class="space-y-4">
    <div class="page-card p-5">
      <h3 class="text-xl font-semibold mb-3">操作日志</h3>
      <div class="flex gap-3">
        <el-date-picker v-model="query.startDate" value-format="YYYY-MM-DD" type="date" placeholder="开始日期" />
        <el-date-picker v-model="query.endDate" value-format="YYYY-MM-DD" type="date" placeholder="结束日期" />
        <el-button type="primary" @click="loadData">查询</el-button>
      </div>
    </div>
    <div class="page-card p-5">
      <el-table :data="logs">
        <el-table-column prop="createdAt" label="时间" width="180" />
        <el-table-column prop="studentId" label="学生ID" width="100" />
        <el-table-column prop="ruleName" label="操作描述" />
        <el-table-column prop="expChange" label="经验变动" width="120" />
        <el-table-column prop="coinChange" label="金币变动" width="120" />
      </el-table>
    </div>
  </div>
</template>
