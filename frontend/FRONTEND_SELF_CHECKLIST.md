# 前端联调自检清单

目的：逐页验证前端功能与后端 API 已联通，确保每个按钮有后端路径或明确占位说明。

## A. 全局检查

- [ ] 前端可启动：`npm run dev`
- [ ] 后端可访问：`http://localhost:8080`
- [ ] 浏览器无明显报错（Console 无红色异常）
- [ ] 登录后本地存储存在：`token/teacherId/username/nickname`
- [ ] 任一受保护接口请求都带有 `Authorization: Bearer <token>`

---

## B. 登录页 `views/Login.vue`

### 按钮：登录

- [ ] 输入正确账号密码点击“登录”后跳转主页
- [ ] 后端接口命中：`POST /api/auth/login`
- [ ] 错误账号时显示错误提示（非静默失败）

---

## C. 主布局 `layouts/MainLayout.vue`

### 导航：宠物乐园 / 小卖部 / 光荣榜 / 百宝箱 / 系统设置

- [ ] 导航点击可切页（前端路由）
- [ ] 班级选择器正常加载班级列表
- [ ] 后端接口命中：`GET /api/classrooms`

### 按钮：退出

- [ ] 点击后清理本地 token 并可回到登录页

---

## D. 宠物乐园 `views/PetGarden.vue`

### 页面加载

- [ ] 正常加载学生宠物卡片
- [ ] 后端接口命中：`GET /api/classrooms/{classId}/students`
- [ ] 规则已加载
- [ ] 后端接口命中：`GET /api/rules`

### 点击学生卡片（未领养）

- [ ] 弹出领养弹窗
- [ ] 输入宠物名、选择宠物蛋后点击确认
- [ ] 后端接口命中：`POST /api/students/{studentId}/pet`
- [ ] 领养后卡片更新

### 打分弹窗（已领养）

- [ ] 点击积极行为规则可成功打分
- [ ] 后端接口命中：`POST /api/score`
- [ ] 点击消极行为规则可成功打分
- [ ] 打分后等级/经验/金币刷新

### 按钮：刷新

- [ ] 可重新拉取最新卡片数据

---

## E. 小卖部 `views/Shop.vue`

### 页面加载

- [ ] 商品列表显示正常
- [ ] 后端接口命中：`GET /api/shop/items`

### 按钮：购买

- [ ] 弹出学生选择对话框
- [ ] 确认购买成功后提示成功
- [ ] 后端接口命中：`POST /api/shop/purchase`
- [ ] 购买后重新拉取商品/学生数据

### 按钮：编辑 / 下架（当前状态）

- [ ] 当前为提示型占位（在系统设置可补全后台管理流程）
- [ ] 不应导致页面报错

### 按钮：刷新

- [ ] 可重新拉取商品与学生数据

---

## F. 光荣榜 `views/Leaderboard.vue`

### 页面加载

- [ ] 宠物榜数据加载
- [ ] 后端接口命中：`GET /api/leaderboard/pet?classId=xx`
- [ ] 金币榜数据加载
- [ ] 后端接口命中：`GET /api/leaderboard/coin?classId=xx`

### 切换标签：宠物榜 / 金币榜

- [ ] 切换后领奖台与列表同步变化
- [ ] 无空白或闪退

---

## G. 系统设置-账号管理 `views/settings/AccountManagement.vue`

### 按钮：保存（修改密码）

- [ ] 提交后提示成功或失败
- [ ] 后端接口命中：`PUT /api/auth/password`

---

## H. 系统设置-班级学生 `views/settings/ClassStudents.vue`

### 按钮：保存（班级名称）

- [ ] 无班级时调用创建
- [ ] 后端接口命中：`POST /api/classrooms`
- [ ] 有班级时调用重命名
- [ ] 后端接口命中：`PUT /api/classrooms/{id}`

### 按钮：批量导入

- [ ] 按换行拆分姓名并成功导入
- [ ] 后端接口命中：`POST /api/classrooms/{classId}/students`

### 按钮：改名

- [ ] 修改学生姓名成功
- [ ] 后端接口命中：`PUT /api/students/{id}`

### 按钮：移除

- [ ] 删除学生成功
- [ ] 后端接口命中：`DELETE /api/students/{id}`

---

## I. 系统设置-分值管理 `views/settings/RuleManagement.vue`

### 按钮：新建规则 / 保存

- [ ] 新建规则成功
- [ ] 后端接口命中：`POST /api/rules`

### 按钮：编辑 / 保存

- [ ] 更新规则成功
- [ ] 后端接口命中：`PUT /api/rules/{id}`

### 按钮：删除

- [ ] 删除规则成功
- [ ] 后端接口命中：`DELETE /api/rules/{id}`

### 页面加载

- [ ] 规则列表可展示
- [ ] 后端接口命中：`GET /api/rules`

---

## J. 系统设置-操作日志 `views/settings/OperationLogs.vue`

### 按钮：查询

- [ ] 按班级、开始/结束日期查询日志
- [ ] 后端接口命中：`GET /api/logs`
- [ ] 表格显示 `ruleName/expChange/coinChange/createdAt` 等字段

---

## K. 系统设置-危险操作区 `views/settings/DangerZone.vue`

- [ ] 页面可正常访问
- [ ] 当前为提示占位，不触发后端写操作

---

## L. 回归验证（关键闭环）

- [ ] 在宠物乐园打分后，光荣榜名次能变化
- [ ] 在小卖部购买后，宠物乐园金币能减少
- [ ] 打分后，操作日志能看到新增记录

若以上三条全部通过，说明前后端主流程已联通。
