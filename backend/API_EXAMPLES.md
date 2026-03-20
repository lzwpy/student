# 后端接口请求示例（前后端联调）

本文档用于前后端联调，示例基于当前后端实现，统一返回格式：

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

## 1. 基础约定

- Base URL: `http://localhost:8080`
- 鉴权头（除 `/api/auth/**` 外均需要）：

```text
Authorization: Bearer <token>
Content-Type: application/json
```

---

## 2. 认证模块

### 2.1 注册

`POST /api/auth/register`

请求体：

```json
{
  "username": "teacher01",
  "password": "123456",
  "nickname": "王老师"
}
```

响应：

```json
{
  "code": 200,
  "msg": "success",
  "data": null
}
```

### 2.2 登录

`POST /api/auth/login`

请求体：

```json
{
  "username": "teacher01",
  "password": "123456"
}
```

响应（保存 `data.token`）：

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9.xxx",
    "teacherId": 1,
    "username": "teacher01",
    "nickname": "王老师"
  }
}
```

### 2.3 修改密码

`PUT /api/auth/password`

请求体：

```json
{
  "oldPassword": "123456",
  "newPassword": "12345678"
}
```

---

## 3. 班级模块

### 3.1 新建班级

`POST /api/classrooms`

请求体：

```json
{
  "name": "四（4）班"
}
```

### 3.2 班级列表

`GET /api/classrooms`

响应示例：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "teacherId": 1,
      "name": "四（4）班",
      "createdAt": "2026-03-19T14:00:00",
      "updatedAt": "2026-03-19T14:00:00"
    }
  ]
}
```

### 3.3 修改班级名

`PUT /api/classrooms/{id}`

请求体：

```json
{
  "name": "四（4）班-重命名"
}
```

### 3.4 删除班级

`DELETE /api/classrooms/{id}`

---

## 4. 学生模块

### 4.1 批量新增学生

`POST /api/classrooms/{classId}/students`

请求体：

```json
{
  "names": ["李昱成", "佟沐宸", "王铂源"]
}
```

### 4.2 获取班级学生（含宠物信息）

`GET /api/classrooms/{classId}/students`

响应示例（未领养宠物时 `petId` 相关字段可能为空）：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "studentId": 1,
      "studentName": "李昱成",
      "sortOrder": 1,
      "petId": null,
      "petName": null,
      "imageKey": null,
      "level": null,
      "exp": null,
      "totalExp": null,
      "coins": 0
    }
  ]
}
```

### 4.3 学生改名

`PUT /api/students/{id}`

请求体：

```json
{
  "name": "李昱成-新"
}
```

### 4.4 删除学生

`DELETE /api/students/{id}`

---

## 5. 宠物模块

### 5.1 领养宠物

`POST /api/students/{studentId}/pet`

请求体：

```json
{
  "petName": "我的刀盾",
  "imageKey": "cat"
}
```

### 5.2 查看宠物详情

`GET /api/students/{studentId}/pet`

响应示例：

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "id": 1,
    "studentId": 1,
    "name": "我的刀盾",
    "imageKey": "cat",
    "level": 1,
    "exp": 0,
    "totalExp": 0,
    "createdAt": "2026-03-19T14:10:00",
    "updatedAt": "2026-03-19T14:10:00"
  }
}
```

### 5.3 修改宠物名

`PUT /api/pets/{id}/name`

请求体：

```json
{
  "petName": "小刀盾"
}
```

---

## 6. 规则模块

### 6.1 新增规则（积极行为）

`POST /api/rules`

请求体：

```json
{
  "name": "积极回答问题",
  "type": "positive",
  "icon": "thumb-up",
  "expValue": 1,
  "coinValue": 1,
  "sortOrder": 1
}
```

### 6.2 新增规则（需要改进）

`POST /api/rules`

请求体：

```json
{
  "name": "上课走神",
  "type": "negative",
  "icon": "warning",
  "expValue": -1,
  "coinValue": -1,
  "sortOrder": 1
}
```

### 6.3 获取规则列表

`GET /api/rules`

### 6.4 更新规则

`PUT /api/rules/{id}`

### 6.5 删除规则

`DELETE /api/rules/{id}`

---

## 7. 打分模块（核心）

### 7.1 单人打分

`POST /api/score`

请求体：

```json
{
  "studentId": 1,
  "ruleId": 1
}
```

成功后会自动：

- 按规则增减宠物 `exp` / `totalExp`
- 按规则增减学生 `coins`（最低为 `0`）
- 更新宠物 `level`
- 写入 `score_log`

### 7.2 批量打分

`POST /api/score/batch`

请求体：

```json
{
  "studentIds": [1, 2, 3],
  "ruleId": 1
}
```

---

## 8. 小卖部模块

### 8.1 新增商品

`POST /api/shop/items`

请求体：

```json
{
  "name": "免作业卡",
  "description": "可抵扣一次作业",
  "image": "item-no-homework.png",
  "price": 10,
  "stock": 20,
  "status": 1
}
```

### 8.2 商品列表

`GET /api/shop/items`

### 8.3 更新商品

`PUT /api/shop/items/{id}`

### 8.4 下架商品

`DELETE /api/shop/items/{id}`

### 8.5 学生购买商品

`POST /api/shop/purchase`

请求体：

```json
{
  "studentId": 1,
  "itemId": 1
}
```

购买成功后会自动扣减学生金币并写入 `purchase_log`。

---

## 9. 光荣榜模块

### 9.1 宠物榜（按总经验）

`GET /api/leaderboard/pet?classId=1`

### 9.2 金币榜

`GET /api/leaderboard/coin?classId=1`

响应示例：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "studentId": 1,
      "studentName": "李昱成",
      "petName": "我的刀盾",
      "imageKey": "cat",
      "level": 2,
      "totalExp": 12,
      "coins": 8
    }
  ]
}
```

说明：

- 金币为学生独立资产，不再存放于宠物表。
- 金币榜中若学生尚未领养宠物，`petName` / `imageKey` / `level` / `totalExp` 可能为空。

---

## 10. 操作日志模块

### 10.1 查询日志

`GET /api/logs?classId=1&startDate=2026-03-01&endDate=2026-03-31`

响应示例：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 10,
      "studentId": 1,
      "classroomId": 1,
      "ruleId": 1,
      "ruleName": "积极回答问题",
      "expChange": 1,
      "coinChange": 1,
      "operatorId": 1,
      "createdAt": "2026-03-19T17:26:00"
    }
  ]
}
```

---

## 11. 建议联调顺序

1. 注册/登录（拿 token）
2. 新建班级
3. 批量新增学生
4. 学生领养宠物
5. 新建加减分规则
6. 执行打分
7. 查询光荣榜
8. 新建商品并执行购买
9. 查看操作日志

