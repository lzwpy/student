<script setup lang="ts">
import { watch, ref } from "vue";
import { ElMessage } from "element-plus";
import { getRecentOperationsApi, revertOperationApi } from "@/api/operations";
import type { RecentOperationItem } from "@/types";
import OperationTimeline from "@/components/OperationTimeline.vue";

const props = withDefaults(defineProps<{
  visible: boolean;
  classId?: number | null;
  studentId?: number | null;
  title?: string;
  limit?: number;
  reloadKey?: number;
}>(), {
  title: "最近操作记录",
  limit: 10,
  reloadKey: 0
});

const emit = defineEmits<{
  "update:visible": [value: boolean];
  reverted: [];
}>();

const loading = ref(false);
const items = ref<RecentOperationItem[]>([]);

async function loadData() {
  if (!props.visible) return;
  loading.value = true;
  try {
    items.value = await getRecentOperationsApi({
      classId: props.classId ?? undefined,
      studentId: props.studentId ?? undefined,
      limit: props.limit
    });
  } finally {
    loading.value = false;
  }
}

async function revertItem(id: number) {
  await revertOperationApi(id);
  ElMessage.success("撤回成功");
  await loadData();
  emit("reverted");
}

watch(
  () => [props.visible, props.classId, props.studentId, props.limit, props.reloadKey],
  () => {
    void loadData();
  },
  { immediate: true }
);
</script>

<template>
  <el-drawer
    :model-value="visible"
    :title="title"
    size="720px"
    @close="emit('update:visible', false)"
  >
    <OperationTimeline
      :items="items"
      :loading="loading"
      empty-text="暂时没有可展示的最近操作"
      @revert="revertItem"
    />
  </el-drawer>
</template>
