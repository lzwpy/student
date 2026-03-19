<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { ElMessage } from "element-plus";
import { getShopItemsApi, purchaseApi } from "@/api/shop";
import { getStudentsApi } from "@/api/student";
import { useClassroomStore } from "@/stores/classroom";
import type { ShopItem, StudentPet } from "@/types";
import ShopItemCard from "@/components/ShopItemCard.vue";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const items = ref<ShopItem[]>([]);
const students = ref<StudentPet[]>([]);
const buyDialog = ref(false);
const buyState = ref({ studentId: 0, itemId: 0 });

async function loadData() {
  items.value = await getShopItemsApi();
  if (classId.value) {
    students.value = await getStudentsApi(classId.value);
  }
}

function openBuy(itemId: number) {
  buyState.value = { studentId: 0, itemId };
  buyDialog.value = true;
}

async function submitBuy() {
  await purchaseApi({ studentId: buyState.value.studentId, itemId: buyState.value.itemId });
  ElMessage.success("购买成功");
  buyDialog.value = false;
  await loadData();
}

onMounted(loadData);
</script>

<template>
  <div class="space-y-4">
    <div class="page-card p-4 flex items-center justify-between">
      <h2 class="text-xl font-semibold">课堂小卖部</h2>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-4">
      <ShopItemCard
        v-for="item in items.filter((v) => v.status === 1)"
        :key="item.id"
        :item="item"
        @buy="openBuy(item.id)"
        @edit="ElMessage.info('编辑功能可在系统设置完善')"
        @remove="ElMessage.info('下架功能可在系统设置完善')"
      />
    </div>
  </div>

  <el-dialog v-model="buyDialog" title="选择购买学生" width="500px">
    <el-select v-model="buyState.studentId" class="w-full" placeholder="请选择学生">
      <el-option v-for="s in students" :key="s.studentId" :label="s.studentName" :value="s.studentId" />
    </el-select>
    <template #footer>
      <el-button @click="buyDialog = false">取消</el-button>
      <el-button type="primary" :disabled="!buyState.studentId" @click="submitBuy">确认购买</el-button>
    </template>
  </el-dialog>
</template>
