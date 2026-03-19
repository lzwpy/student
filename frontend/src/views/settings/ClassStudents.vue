<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useClassroomStore } from "@/stores/classroom";
import { createClassroomApi, renameClassroomApi } from "@/api/classroom";
import { createStudentsApi, deleteStudentApi, getStudentsApi, renameStudentApi } from "@/api/student";
import type { StudentPet } from "@/types";
import { ElMessage } from "element-plus";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const classForm = ref({ name: "" });
const studentsInput = ref("");
const students = ref<StudentPet[]>([]);

async function loadData() {
  await classroomStore.loadClassrooms();
  if (classId.value) students.value = await getStudentsApi(classId.value);
}

async function saveClassName() {
  if (!classForm.value.name) return;
  if (!classId.value) {
    await createClassroomApi({ name: classForm.value.name });
  } else {
    await renameClassroomApi(classId.value, { name: classForm.value.name });
  }
  ElMessage.success("班级已保存");
  classForm.value.name = "";
  await loadData();
}

async function addStudents() {
  if (!classId.value) return;
  const names = studentsInput.value
    .split("\n")
    .map((v) => v.trim())
    .filter(Boolean);
  if (!names.length) return;
  await createStudentsApi(classId.value, { names });
  ElMessage.success("学生已添加");
  studentsInput.value = "";
  await loadData();
}

async function renameStudent(id: number) {
  const name = window.prompt("输入新学生姓名");
  if (!name) return;
  await renameStudentApi(id, { name });
  await loadData();
}

async function removeStudent(id: number) {
  await deleteStudentApi(id);
  await loadData();
}

onMounted(loadData);
</script>

<template>
  <div class="space-y-4">
    <div class="page-card p-5">
      <h3 class="text-xl font-semibold mb-4">班级学生</h3>
      <div class="flex gap-3">
        <el-input v-model="classForm.name" placeholder="输入新班级名称" class="max-w-sm" />
        <el-button type="primary" @click="saveClassName">保存</el-button>
      </div>
    </div>

    <div class="page-card p-5">
      <h4 class="font-semibold mb-3">新增学生（支持换行批量导入）</h4>
      <el-input v-model="studentsInput" type="textarea" :rows="5" placeholder="每行一个姓名" />
      <el-button class="mt-3" type="success" @click="addStudents">批量导入</el-button>
    </div>

    <div class="page-card p-5">
      <h4 class="font-semibold mb-3">当前学生名册</h4>
      <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-3">
        <div v-for="s in students" :key="s.studentId" class="border border-slate-100 rounded-xl p-3 flex justify-between">
          <span class="font-medium">{{ s.studentName }}</span>
          <span class="space-x-2">
            <el-button size="small" @click="renameStudent(s.studentId)">改名</el-button>
            <el-button size="small" type="danger" plain @click="removeStudent(s.studentId)">移除</el-button>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>
