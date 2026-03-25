# 页面改造清单

## 主框架
- `src/layouts/MainLayout.vue`
  - 顶部导航改为卡通标签导航
  - 顶栏加入品牌区和更柔和的班级选择器
  - 主内容区增加背景层与统一留白
- `src/layouts/SettingsLayout.vue`
  - 左侧菜单改为圆角导航卡
  - 激活态更突出

## 宠物乐园
- `src/views/PetGarden.vue`
  - 页面头部改为欢迎卡+刷新按钮
  - 学生宠物卡网格留白更大
  - 领养弹窗改成卡通表单弹窗
- `src/components/PetCard.vue`
  - 增加等级角标、金币芯片、经验进度底条
  - 重构插画摆放和名字层级
- `src/components/ScorePanel.vue`
  - 头部显示学生与宠物信息
  - 正向/负向规则切分成两列卡片组
  - 增加最近操作记录入口

## 小卖部
- `src/views/Shop.vue`
  - 页面头部和筛选信息升级
  - 购买弹窗升级为统一视觉
- `src/components/ShopItemCard.vue`
  - 增加商品插画位、价格芯片、库存状态
  - 调整购买按钮主次关系

## 光荣榜
- `src/views/Leaderboard.vue`
  - 主标题卡更接近活动榜单
  - 排名切换器做胶囊样式
  - 表格外层弱化后台感
- `src/components/LeaderboardPodium.vue`
  - 前三名卡片高低错落
  - 增加奖牌感标签

## 操作记录
- `src/views/settings/OperationLogs.vue`
  - 从单纯表格页升级为“完整日志页”
  - 同时保留最近操作卡片式列表
- 新增最近操作面板组件
  - 在 `ScorePanel` 中可直接打开
  - 支持撤回、已撤回状态、动作类型标签

## 资源与占位
- `public/mockups/`
  - 保存本次风格草图 SVG
- `src/assets/icons/`
  - 放统一图标 SVG 组件或图标文件
- `public/illustrations/`
  - 预留卡通宠物、商品和界面贴纸资源
