<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
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
import AppIcon from "@/components/AppIcon.vue";
import RecentOperationsDrawer from "@/components/RecentOperationsDrawer.vue";

const classroomStore = useClassroomStore();
const students = ref<StudentPet[]>([]);
const rules = ref<RuleItem[]>([]);
const scorePanelVisible = ref(false);
const selectedStudent = ref<StudentPet | null>(null);
const adoptVisible = ref(false);
const operationsVisible = ref(false);
const operationsReloadKey = ref(0);
const adoptForm = ref({ petName: "", imageKey: "cat_1" });

const classId = computed(() => classroomStore.activeClassId);

async function loadData() {
  if (!classId.value) {
    students.value = [];
    return;
  }
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
  operationsReloadKey.value += 1;
}

onMounted(loadData);
watch(classId, () => {
  void loadData();
});
</script>

<template>
  <div class="space-y-4">
    <div class="grid gap-4 xl:grid-cols-[1.5fr,0.9fr]">
      <section class="app-card p-6">
        <div class="flex flex-col gap-6 md:flex-row md:items-center md:justify-between">
          <div class="flex items-start gap-4">
            <AppIcon name="pet" tone="mint" :size="28" />
            <div>
              <p class="text-sm font-semibold tracking-[0.2em] text-slate-400">Pet Garden</p>
              <h2 class="mt-2 text-3xl font-black text-ink">宠物乐园</h2>
              <p class="mt-2 text-sm leading-6 text-slate-500">
                点击任意学生卡片即可查看宠物详情、记录课堂表现，并在最近操作记录中撤回误操作。
              </p>
            </div>
          </div>
          <div class="flex flex-wrap gap-3">
            <el-button @click="operationsVisible = true">
              <AppIcon name="history" tone="slate" :size="14" />
              最近操作
            </el-button>
            <el-button type="primary" @click="loadData">
              <AppIcon name="refresh" tone="dark" :size="14" />
              刷新
            </el-button>
          </div>
        </div>
      </section>

      <aside class="app-card-soft p-5">
        <div class="flex items-center gap-3">
          <AppIcon name="spark" tone="gold" :size="18" />
          <span class="text-lg font-bold text-ink">今日小提示</span>
        </div>
        <ul class="mt-4 space-y-3 text-sm leading-6 text-slate-500">
          <li>积极行为会同步增加宠物 EXP 和金币。</li>
          <li>误点规则后可在最近操作记录里快速撤回。</li>
          <li>未领养宠物的同学会先进入领养弹窗。</li>
        </ul>
      </aside>
    </div>

    <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4 xl:grid-cols-5">
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
    :reload-key="operationsReloadKey"
    @score="doScore"
    @reverted="loadData"
  />

  <el-dialog v-model="adoptVisible" title="领养宠物" width="500px">
    <div class="mb-5 flex items-center gap-3 rounded-3xl bg-orange-50 px-4 py-3 text-sm text-slate-600">
      <AppIcon name="gift" tone="gold" :size="18" />
      为这位同学挑选一只卡通风格的小宠物，后续行为记录都会在这里成长。
    </div>
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

  <RecentOperationsDrawer
    v-model:visible="operationsVisible"
    :class-id="classId"
    :reload-key="operationsReloadKey"
    title="全班最近操作"
    @reverted="loadData"
  />
</template>
