# 后端实现进度（当前轮次）

## 已完成

- 基础工程与配置
  - `backend/pom.xml`
  - `backend/src/main/resources/application.yml`
  - `backend/src/main/resources/db/schema.sql`
  - `backend/src/main/java/com/petclass/PetClassApplication.java`
  - `backend/src/main/java/com/petclass/config/*`
- 公共层
  - `common/Result.java`
  - `common/JwtUtils.java`
  - `common/AuthUser.java`
  - `common/JwtAuthenticationFilter.java`
  - `common/GlobalExceptionHandler.java`
  - `common/SecurityUtils.java`
- 实体层
  - `Teacher/Classroom/Student/Pet/Rule/ScoreLog/ShopItem/PurchaseLog`
- Mapper 层
  - `TeacherMapper/ClassroomMapper/StudentMapper/PetMapper/RuleMapper/ScoreLogMapper/ShopItemMapper/PurchaseLogMapper`
- DTO/VO
  - `AuthDtos/ClassroomDtos/StudentDtos/PetDtos/RuleDtos/ScoreDtos/ShopDtos/LogQueryDto`
  - `AuthLoginVO/StudentPetVO/LeaderboardItemVO`
- Service 层
  - `AuthService/ClassroomService/StudentService/PetService/RuleService/ScoreService/ShopService/LeaderboardService`
  - 对应 `impl` 已实现
- Controller 层
  - `AuthController`
  - `ClassroomController`
  - `StudentController`
  - `PetController`
  - `RuleController`
  - `ScoreController`
  - `ShopController`
  - `LeaderboardController`
  - `LogController`

## 说明

- 当前环境未安装 Maven，无法在本机执行 `mvn compile` 做编译校验。
- IDE lints 检查结果：当前后端目录无报错。
