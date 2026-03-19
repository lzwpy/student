import { createRouter, createWebHistory } from "vue-router";
import MainLayout from "@/layouts/MainLayout.vue";
import SettingsLayout from "@/layouts/SettingsLayout.vue";
import Login from "@/views/Login.vue";
import PetGarden from "@/views/PetGarden.vue";
import Shop from "@/views/Shop.vue";
import Leaderboard from "@/views/Leaderboard.vue";
import TreasureBox from "@/views/TreasureBox.vue";
import AccountManagement from "@/views/settings/AccountManagement.vue";
import ClassStudents from "@/views/settings/ClassStudents.vue";
import RuleManagement from "@/views/settings/RuleManagement.vue";
import OperationLogs from "@/views/settings/OperationLogs.vue";
import DangerZone from "@/views/settings/DangerZone.vue";
import { useAuthStore } from "@/stores/auth";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/login", name: "login", component: Login },
    {
      path: "/",
      component: MainLayout,
      redirect: "/pet-garden",
      children: [
        { path: "pet-garden", name: "petGarden", component: PetGarden },
        { path: "shop", name: "shop", component: Shop },
        { path: "leaderboard", name: "leaderboard", component: Leaderboard },
        { path: "treasure-box", name: "treasureBox", component: TreasureBox },
        {
          path: "settings",
          component: SettingsLayout,
          redirect: "/settings/account",
          children: [
            { path: "account", name: "account", component: AccountManagement },
            { path: "class-students", name: "classStudents", component: ClassStudents },
            { path: "rules", name: "rules", component: RuleManagement },
            { path: "logs", name: "logs", component: OperationLogs },
            { path: "danger", name: "danger", component: DangerZone }
          ]
        }
      ]
    }
  ]
});

router.beforeEach((to) => {
  const authStore = useAuthStore();
  if (to.path !== "/login" && !authStore.isLogin) return "/login";
  if (to.path === "/login" && authStore.isLogin) return "/";
  return true;
});

export default router;
