<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { getCoinRankApi, getPetRankApi } from "@/api/leaderboard";
import { useClassroomStore } from "@/stores/classroom";
import type { LeaderboardItem } from "@/types";
import LeaderboardPodium from "@/components/LeaderboardPodium.vue";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const activeTab = ref<"pet" | "coin">("pet");
const petRank = ref<LeaderboardItem[]>([]);
const coinRank = ref<LeaderboardItem[]>([]);

async function loadData() {
  if (!classId.value) return;
  petRank.value = await getPetRankApi(classId.value);
  coinRank.value = await getCoinRankApi(classId.value);
}

const list = computed(() => (activeTab.value === "pet" ? petRank.value : coinRank.value));
onMounted(loadData);
</script>

<template>
  <div class="space-y-4">
    <div class="page-card p-4">
      <h2 class="text-2xl font-bold text-center">光荣榜</h2>
      <div class="flex justify-center mt-4">
        <el-segmented v-model="activeTab" :options="[{label:'宠物榜',value:'pet'},{label:'金币榜',value:'coin'}]" />
      </div>
    </div>
    <LeaderboardPodium :items="list" />
    <div class="page-card p-4">
      <el-table :data="list.slice(3)" style="width:100%">
        <el-table-column type="index" label="排名" width="80" />
        <el-table-column prop="studentName" label="学生" />
        <el-table-column prop="petName" label="宠物" />
        <el-table-column prop="level" label="等级" />
        <el-table-column prop="totalExp" label="总经验" />
        <el-table-column prop="coins" label="金币" />
      </el-table>
    </div>
  </div>
</template>
