# 积分养宠物系统前端

基于 `Vue 3 + Vite + TypeScript + Pinia + Vue Router + Element Plus + TailwindCSS`。

## 1. 环境要求

- Node.js >= 18（推荐 20）
- npm / pnpm（推荐 pnpm）

## 2. 安装依赖

在 `frontend/` 目录执行：

```bash
npm install
```

或：

```bash
pnpm install
```

## 3. 启动项目

开发环境启动：

```bash
npm run dev
```

默认访问地址：

- 前端：`http://localhost:5173`

## 4. 构建与预览

```bash
npm run build
npm run preview
```

## 5. 环境变量说明

当前项目通过 `vite.config.ts` 将 `/api` 代理到后端：

- 前端请求：`/api/**`
- 代理目标：`http://localhost:8080`

因此本地开发默认无需额外环境变量。

如需切换后端地址，修改 `vite.config.ts` 中：

```ts
server: {
  proxy: {
    "/api": {
      target: "http://localhost:8080"
    }
  }
}
```

可选约定（如后续改为环境变量模式）：

- `VITE_API_BASE_URL`：后端地址（示例：`http://localhost:8080`）

## 6. 与后端联调前准备

1. 确认后端已启动（`http://localhost:8080`）。
2. 确认数据库已初始化（执行后端 `schema.sql`）。
3. 前端登录后会自动把 token 存到 `localStorage`：
   - `token`
   - `teacherId`
   - `username`
   - `nickname`
4. 所有受保护接口请求会自动在请求头中附加：
   - `Authorization: Bearer <token>`

## 7. 核心目录说明

```text
frontend/src
├── api/          # Axios 请求封装
├── components/   # 可复用组件（宠物卡片、打分弹窗等）
├── layouts/      # 主布局、设置页布局
├── router/       # 路由与登录守卫
├── stores/       # Pinia 状态
├── types/        # 类型定义
├── utils/        # 工具函数（等级计算）
└── views/        # 页面
```

## 8. 联调步骤（建议顺序）

1. 打开登录页，使用教师账号登录。
2. 进入系统设置 -> 班级学生，先创建班级并导入学生。
3. 进入系统设置 -> 分值管理，创建积极和消极规则。
4. 回到宠物乐园，给学生领养宠物并执行打分。
5. 进入光荣榜，验证宠物榜/金币榜变化。
6. 进入小卖部，验证商品展示与购买扣币。
7. 进入操作日志，验证打分日志是否落库并显示。

## 9. 常见问题

- 登录后仍跳回登录页：
  - 检查浏览器 `localStorage` 是否有 `token`
  - 检查后端是否返回 `code=200`
- 请求 404：
  - 检查后端控制器路径是否为 `/api/**`
  - 检查 `vite.config.ts` 代理是否生效
- 请求 401：
  - token 失效，重新登录
  - 检查请求头是否包含 `Bearer` 前缀
