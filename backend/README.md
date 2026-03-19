# 积分养宠物学生管理系统后端

基于 `Spring Boot 3 + MyBatis-Plus + Spring Security + JWT + MySQL 8` 的后端服务。

## 1. 技术栈

- Java 17
- Spring Boot 3.2.5
- MyBatis-Plus 3.5.6
- Spring Security + JWT
- MySQL 8
- Maven

## 2. 项目结构

```text
backend/
├── pom.xml
├── README.md
└── src/main
    ├── java/com/petclass
    │   ├── common/       # 通用返回、JWT、异常处理、安全工具
    │   ├── config/       # 安全、跨域、MyBatis-Plus 配置
    │   ├── controller/   # 接口层
    │   ├── dto/          # 请求 DTO
    │   ├── entity/       # 实体
    │   ├── mapper/       # MyBatis-Plus Mapper
    │   ├── service/      # 业务接口
    │   ├── service/impl/ # 业务实现
    │   └── vo/           # 响应 VO
    └── resources
        ├── application.yml
        └── db/schema.sql
```

## 3. 运行前准备

1. 安装 Java 17，并配置 `JAVA_HOME`。
2. 安装 Maven（确保命令行可执行 `mvn -v`）。
3. 安装并启动 MySQL 8。
4. 修改 `src/main/resources/application.yml` 中数据库配置：
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`

## 4. 初始化数据库

执行以下 SQL 文件：

```sql
source backend/src/main/resources/db/schema.sql;
```

该脚本会创建数据库 `pet_class` 和以下表：

- `teacher`
- `classroom`
- `student`
- `pet`
- `rule`
- `score_log`
- `shop_item`
- `purchase_log`

## 5. 启动项目

在 `backend/` 目录执行：

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

默认启动地址：

- 后端服务: `http://localhost:8080`

## 6. 认证说明

- 登录接口：`POST /api/auth/login`
- 登录成功返回 JWT token。
- 访问受保护接口时，需在请求头携带：

```text
Authorization: Bearer <token>
```

## 7. 核心接口概览

### 认证模块

- `POST /api/auth/login`
- `POST /api/auth/register`
- `PUT /api/auth/password`

### 班级模块

- `GET /api/classrooms`
- `POST /api/classrooms`
- `PUT /api/classrooms/{id}`
- `DELETE /api/classrooms/{id}`

### 学生模块

- `GET /api/classrooms/{classId}/students`
- `POST /api/classrooms/{classId}/students`
- `PUT /api/students/{id}`
- `DELETE /api/students/{id}`

### 宠物模块

- `POST /api/students/{studentId}/pet`
- `GET /api/students/{studentId}/pet`
- `PUT /api/pets/{id}/name`

### 打分模块

- `POST /api/score`
- `POST /api/score/batch`

### 规则模块

- `GET /api/rules`
- `POST /api/rules`
- `PUT /api/rules/{id}`
- `DELETE /api/rules/{id}`

### 小卖部模块

- `GET /api/shop/items`
- `POST /api/shop/items`
- `PUT /api/shop/items/{id}`
- `DELETE /api/shop/items/{id}`
- `POST /api/shop/purchase`

### 光荣榜

- `GET /api/leaderboard/pet?classId=xx`
- `GET /api/leaderboard/coin?classId=xx`

### 操作日志

- `GET /api/logs?classId=xx&startDate=yyyy-MM-dd&endDate=yyyy-MM-dd`

## 8. 开发注意事项

- 当前实现使用 `Result<T>` 统一响应格式：`{ code, msg, data }`。
- 业务异常以 `IllegalArgumentException` 抛出，并由全局异常处理器转换为统一返回。
- 宠物等级逻辑在 `PetServiceImpl` 中实现：
  - Lv.1: `0+`
  - Lv.2: `10+`
  - Lv.3: `25+`
  - Lv.4: `50+`
  - Lv.5: `100+`

## 9. 下一步建议

1. 增加接口文档（建议引入 SpringDoc / OpenAPI）。
2. 增加单元测试与集成测试。
3. 增加数据库迁移工具（Flyway/Liquibase）。
4. 根据前端联调结果补充分页、筛选和操作审计字段。
