<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useClassroomStore } from "@/stores/classroom";

const classroomStore = useClassroomStore();
const activeClass = computed(() =>
  classroomStore.classrooms.find((v) => v.id === classroomStore.activeClassId)
);

onMounted(async () => {
  if (!classroomStore.classrooms.length) {
    await classroomStore.loadClassrooms();
  }
});
</script>

<template>
  <el-select
    :model-value="classroomStore.activeClassId ?? undefined"
    placeholder="请选择班级"
    class="w-48"
    @update:model-value="(v:number) => classroomStore.setActiveClass(v)"
  >
    <el-option
      v-for="item in classroomStore.classrooms"
      :key="item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>
  <span class="ml-3 text-sm text-slate-500">{{ activeClass?.name || "未选择班级" }}</span>
</template>
