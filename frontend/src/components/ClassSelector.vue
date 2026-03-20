<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useClassroomStore } from "@/stores/classroom";
import AppIcon from "@/components/AppIcon.vue";

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
  <div class="flex flex-wrap items-center gap-3">
    <div class="app-card-soft flex items-center gap-3 px-4 py-2">
      <AppIcon name="student" tone="mint" :size="18" />
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
    </div>
    <span class="app-chip-primary text-sm">{{ activeClass?.name || "未选择班级" }}</span>
  </div>
</template>
