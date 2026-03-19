<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useClassroomStore } from "@/stores/classroom";
import { getStudentsApi } from "@/api/student";
import { getRulesApi } from "@/api/rule";
import { scoreApi } from "@/api/score";
import { adoptPetApi } from "@/api/pet";
import type { RuleItem, StudentPet } from "@/types";
import { ElMessage } from "element-plus";
import ScorePanel from "@/components/ScorePanel.vue";
import PetCard from "@/components/PetCard.vue";
import PetEggSelector from "@/components/PetEggSelector.vue";

const classroomStore = useClassroomStore();
const students = ref<StudentPet[]>([]);
const rules = ref<RuleItem[]>([]);
const scorePanelVisible = ref(false);
const selectedStudent = ref<StudentPet | null>(null);
const adoptVisible = ref(false);
const adoptForm = ref({ petName: "", imageKey: "cat_1" });

const classId = computed(() => classroomStore.activeClassId);

async function loadData() {
  if (!classId.value) return;
  students.value = await getStudentsApi(classId.value);
  rules.value = await getRulesApi();
}

function openStudent(item: StudentPet) {
  selectedStudent.value = item;
  if (!item.petId) {
    adoptForm.value.petName = `${item.studentName}的宠物`;
    adoptVisible.value = true;
    return;
  }
  scorePanelVisible.value = true;
}

async function submitAdopt() {
  if (!selectedStudent.value) return;
  await adoptPetApi(selectedStudent.value.studentId, adoptForm.value);
  ElMessage.success("领养成功");
  adoptVisible.value = false;
  await loadData();
  scorePanelVisible.value = true;
}

async function doScore(ruleId: number) {
  if (!selectedStudent.value) return;
  await scoreApi({ studentId: selectedStudent.value.studentId, ruleId });
  ElMessage.success("打分成功");
  await loadData();
}

onMounted(loadData);
</script>

<template>
  <div class="space-y-4">
    <div class="page-card p-4 flex justify-between items-center">
      <h2 class="text-xl font-semibold">宠物乐园</h2>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 xl:grid-cols-5 gap-4">
      <button
        v-for="item in students"
        :key="item.studentId"
        class="text-left"
        @click="openStudent(item)"
      >
        <PetCard :item="item" />
      </button>
    </div>
  </div>

  <ScorePanel
    v-model:visible="scorePanelVisible"
    :student="selectedStudent"
    :rules="rules"
    @score="doScore"
  />

  <el-dialog v-model="adoptVisible" title="领养宠物" width="500px">
    <el-form label-width="90px">
      <el-form-item label="宠物名称">
        <el-input v-model="adoptForm.petName" />
      </el-form-item>
      <el-form-item label="宠物蛋">
        <PetEggSelector v-model="adoptForm.imageKey" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="adoptVisible = false">取消</el-button>
      <el-button type="primary" @click="submitAdopt">确认领养</el-button>
    </template>
  </el-dialog>
</template>
