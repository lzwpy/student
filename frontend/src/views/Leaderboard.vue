<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { getCoinRankApi, getPetRankApi } from "@/api/leaderboard";
import { useClassroomStore } from "@/stores/classroom";
import type { LeaderboardItem } from "@/types";
import LeaderboardPodium from "@/components/LeaderboardPodium.vue";
import AppIcon from "@/components/AppIcon.vue";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const activeTab = ref<"pet" | "coin">("pet");
const petRank = ref<LeaderboardItem[]>([]);
const coinRank = ref<LeaderboardItem[]>([]);

async function loadData() {
  if (!classId.value) {
    petRank.value = [];
    coinRank.value = [];
    return;
  }
  petRank.value = await getPetRankApi(classId.value);
  coinRank.value = await getCoinRankApi(classId.value);
}

const list = computed(() => (activeTab.value === "pet" ? petRank.value : coinRank.value));
onMounted(loadData);
watch(classId, () => {
  void loadData();
});
</script>

<template>
  <div class="space-y-4">
    <div class="app-card p-6 text-center">
      <div class="flex justify-center">
        <AppIcon name="trophy" tone="gold" :size="28" />
      </div>
      <h2 class="mt-3 text-3xl font-black text-ink">光荣榜</h2>
      <p class="mt-2 text-sm text-slate-500">给表现优秀和成长快速的同学一个更有仪式感的展示区。</p>
      <div class="mt-5 flex justify-center">
        <el-segmented v-model="activeTab" :options="[{label:'宠物榜',value:'pet'},{label:'金币榜',value:'coin'}]" />
      </div>
    </div>
    <LeaderboardPodium :items="list" />
    <div class="app-card p-5">
      <div class="mb-4 flex items-center gap-3">
        <AppIcon name="star" tone="mint" :size="18" />
        <h3 class="text-xl font-bold text-ink">完整榜单</h3>
      </div>
      <el-table :data="list.slice(3)" style="width:100%">
        <el-table-column type="index" label="排名" width="80" />
        <el-table-column prop="studentName" label="学生" />
        <el-table-column label="宠物">
          <template #default="{ row }">
            {{ row.petName || "未领养" }}
          </template>
        </el-table-column>
        <el-table-column label="等级">
          <template #default="{ row }">
            {{ row.level ?? "-" }}
          </template>
        </el-table-column>
        <el-table-column label="总经验">
          <template #default="{ row }">
            {{ row.totalExp ?? "-" }}
          </template>
        </el-table-column>
        <el-table-column prop="coins" label="金币" />
      </el-table>
    </div>
  </div>
</template>
