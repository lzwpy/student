# 前端实现进度

## 已完成模块

- 工程初始化
  - `frontend/package.json`
  - `frontend/vite.config.ts`
  - `frontend/tsconfig*.json`
  - `frontend/tailwind.config.js`
  - `frontend/postcss.config.js`
- 入口与基础
  - `frontend/index.html`
  - `frontend/src/main.ts`
  - `frontend/src/App.vue`
  - `frontend/src/styles.css`
- 路由与状态
  - `frontend/src/router/index.ts`
  - `frontend/src/stores/auth.ts`
  - `frontend/src/stores/classroom.ts`
  - `frontend/src/stores/app.ts`
- API 封装
  - `frontend/src/api/*`（认证、班级、学生、宠物、规则、打分、小卖部、光荣榜、日志）
- 页面与组件
  - `frontend/src/layouts/MainLayout.vue`
  - `frontend/src/layouts/SettingsLayout.vue`
  - `frontend/src/views/Login.vue`
  - `frontend/src/views/PetGarden.vue`
  - `frontend/src/views/Shop.vue`
  - `frontend/src/views/Leaderboard.vue`
  - `frontend/src/views/TreasureBox.vue`
  - `frontend/src/views/settings/*`
  - `frontend/src/components/*`
- 宠物资源
  - `frontend/public/pets/cat_1.svg`
  - `frontend/public/pets/dog_1.svg`
  - `frontend/public/pets/penguin_1.svg`
  - `frontend/public/pets/tiger_1.svg`

## 说明

- 已对接现有后端接口路径与返回格式 `{ code, msg, data }`。
- 页面流程已覆盖：登录、班级切换、学生宠物卡片、打分弹窗、光荣榜、小卖部、系统设置四个子页。
