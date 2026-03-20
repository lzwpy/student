<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { getShopItemsApi, purchaseApi } from "@/api/shop";
import { getStudentsApi } from "@/api/student";
import { useClassroomStore } from "@/stores/classroom";
import type { ShopItem, StudentPet } from "@/types";
import ShopItemCard from "@/components/ShopItemCard.vue";
import AppIcon from "@/components/AppIcon.vue";
import RecentOperationsDrawer from "@/components/RecentOperationsDrawer.vue";

const classroomStore = useClassroomStore();
const classId = computed(() => classroomStore.activeClassId);
const items = ref<ShopItem[]>([]);
const students = ref<StudentPet[]>([]);
const buyDialog = ref(false);
const operationsVisible = ref(false);
const operationsReloadKey = ref(0);
const buyState = ref({ studentId: 0, itemId: 0 });

async function loadData() {
  items.value = await getShopItemsApi();
  if (classId.value) {
    students.value = await getStudentsApi(classId.value);
  } else {
    students.value = [];
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
  operationsReloadKey.value += 1;
}

onMounted(loadData);
watch(classId, () => {
  void loadData();
});
</script>

<template>
  <div class="space-y-4">
    <div class="grid gap-4 xl:grid-cols-[1.45fr,0.95fr]">
      <section class="app-card p-6">
        <div class="flex flex-col gap-6 md:flex-row md:items-center md:justify-between">
          <div class="flex items-start gap-4">
            <AppIcon name="shop" tone="gold" :size="28" />
            <div>
              <p class="text-sm font-semibold tracking-[0.2em] text-slate-400">Class Shop</p>
              <h2 class="mt-2 text-3xl font-black text-ink">课堂小卖部</h2>
              <p class="mt-2 text-sm leading-6 text-slate-500">
                统一成卡通礼物卡片风格，购买后会进入最近操作记录，并支持在记录面板中撤回。
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
          <AppIcon name="gift" tone="mint" :size="18" />
          <span class="text-lg font-bold text-ink">小卖部规则</span>
        </div>
        <ul class="mt-4 space-y-3 text-sm leading-6 text-slate-500">
          <li>购买时为学生扣除金币，并记录一条最近操作。</li>
          <li>有限库存商品在撤回时会自动回补库存。</li>
          <li>未完成的编辑和下架功能仍保留入口样式位。</li>
        </ul>
      </aside>
    </div>

    <div class="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
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
    <div class="mb-4 flex items-center gap-3 rounded-3xl bg-orange-50 px-4 py-3 text-sm text-slate-600">
      <AppIcon name="student" tone="gold" :size="18" />
      选择购买学生后，系统会自动扣除金币并写入最近操作记录。
    </div>
    <el-select v-model="buyState.studentId" class="w-full" placeholder="请选择学生">
      <el-option v-for="s in students" :key="s.studentId" :label="s.studentName" :value="s.studentId" />
    </el-select>
    <template #footer>
      <el-button @click="buyDialog = false">取消</el-button>
      <el-button type="primary" :disabled="!buyState.studentId" @click="submitBuy">确认购买</el-button>
    </template>
  </el-dialog>

  <RecentOperationsDrawer
    v-model:visible="operationsVisible"
    :class-id="classId"
    :reload-key="operationsReloadKey"
    title="小卖部最近操作"
    @reverted="loadData"
  />
</template>
