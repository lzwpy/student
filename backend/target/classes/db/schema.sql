-- =============================================================================
-- pet_class 生产级建表脚本（MySQL 8.0+）
-- 字符集 utf8mb4，InnoDB；外键约束 + 表/列注释；业务列 NOT NULL + 合理默认值
-- =============================================================================

CREATE DATABASE IF NOT EXISTS pet_class
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE pet_class;

SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 教师表
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS teacher (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    username        VARCHAR(50)     NOT NULL COMMENT '登录账号，全局唯一',
    password_hash   VARCHAR(255)    NOT NULL COMMENT 'BCrypt 等算法哈希后的密码',
    nickname        VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '昵称/显示名',
    avatar          VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '头像 URL 或资源键',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_teacher_username (username)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='教师账号';

-- -----------------------------------------------------------------------------
-- 班级表
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS classroom (
    id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    teacher_id  BIGINT          NOT NULL COMMENT '所属教师 ID',
    name        VARCHAR(100)    NOT NULL COMMENT '班级名称',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_classroom_teacher_id (teacher_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='班级';

-- -----------------------------------------------------------------------------
-- 学生表
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS student (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    classroom_id    BIGINT          NOT NULL COMMENT '所属班级 ID',
    name            VARCHAR(50)     NOT NULL COMMENT '学生姓名',
    sort_order      INT             NOT NULL DEFAULT 0 COMMENT '班级内展示排序，越小越靠前',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_student_classroom_id (classroom_id)

) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='学生';

-- -----------------------------------------------------------------------------
-- 宠物表（每名学生至多一只）
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pet (
    id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    student_id  BIGINT          NOT NULL COMMENT '学生 ID，一对一',
    name        VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '宠物昵称',
    image_key   VARCHAR(50)     NOT NULL DEFAULT 'cat' COMMENT '形象资源键',
    level       INT             NOT NULL DEFAULT 1 COMMENT '等级',
    exp         INT             NOT NULL DEFAULT 0 COMMENT '当前经验，可为负数',
    total_exp   INT             NOT NULL DEFAULT 0 COMMENT '累计总经验，可为负数',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_pet_student_id (student_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='学生宠物';

-- -----------------------------------------------------------------------------
-- 学生金币表（每名学生一份独立金币资产）
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS student_coin (
    id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    student_id  BIGINT          NOT NULL COMMENT '学生 ID，一对一',
    coins       INT             NOT NULL DEFAULT 0 COMMENT '金币余额，最低为 0',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_student_coin_student_id (student_id),
    CHECK (coins >= 0)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='学生金币资产';

-- -----------------------------------------------------------------------------
-- 评分规则表（教师维度）
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS rule (
    id          BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    teacher_id  BIGINT          NOT NULL COMMENT '所属教师 ID',
    name        VARCHAR(100)    NOT NULL COMMENT '规则名称',
    type        VARCHAR(20)     NOT NULL COMMENT '规则类型：positive 加分 / negative 扣分',
    icon        VARCHAR(50)     NOT NULL DEFAULT 'star' COMMENT '前端展示用图标键',
    exp_value   INT             NOT NULL DEFAULT 0 COMMENT '单次操作经验变化（可正可负由业务解释）',
    coin_value  INT             NOT NULL DEFAULT 0 COMMENT '单次操作金币变化',
    sort_order  INT             NOT NULL DEFAULT 0 COMMENT '展示排序',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_rule_teacher_id (teacher_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='课堂评分规则';

-- -----------------------------------------------------------------------------
-- 打分记录表
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS score_log (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    student_id      BIGINT          NOT NULL COMMENT '被评分学生 ID',
    classroom_id    BIGINT          NOT NULL COMMENT '当时所在班级 ID（冗余便于按班查询）',
    rule_id         BIGINT          NULL COMMENT '关联规则 ID；规则删除后可置空，保留 rule_name 快照',
    rule_name       VARCHAR(100)    NOT NULL COMMENT '规则名称快照',
    exp_change      INT             NOT NULL DEFAULT 0 COMMENT '本次经验变化',
    coin_change     INT             NOT NULL DEFAULT 0 COMMENT '本次金币变化',
    operator_id     BIGINT          NOT NULL COMMENT '操作教师 ID',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '打分时间',
    PRIMARY KEY (id),
    KEY idx_score_log_student_id (student_id),
    KEY idx_score_log_classroom_id (classroom_id),
    KEY idx_score_log_created_at (created_at),
    KEY idx_score_log_rule_id (rule_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='课堂打分流水';

-- -----------------------------------------------------------------------------
-- 商品表（教师小店）
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS shop_item (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    teacher_id      BIGINT          NOT NULL COMMENT '所属教师 ID',
    name            VARCHAR(100)    NOT NULL COMMENT '商品名称',
    description     VARCHAR(500)    NOT NULL DEFAULT '' COMMENT '商品描述',
    image           VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '商品图 URL 或键',
    price           INT             NOT NULL DEFAULT 0 COMMENT '售价（金币）',
    stock           INT             NOT NULL DEFAULT -1 COMMENT '库存；-1 表示无限',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '上架状态：0 下架 1 上架',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_shop_item_teacher_id (teacher_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='班级商店商品';

-- -----------------------------------------------------------------------------
-- 购买记录表
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS purchase_log (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    student_id      BIGINT          NOT NULL COMMENT '购买学生 ID',
    shop_item_id    BIGINT          NOT NULL COMMENT '商品 ID',
    item_name       VARCHAR(100)    NOT NULL COMMENT '商品名称快照',
    price           INT             NOT NULL DEFAULT 0 COMMENT '成交单价（金币）快照',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
    PRIMARY KEY (id),
    KEY idx_purchase_log_student_id (student_id),
    KEY idx_purchase_log_shop_item_id (shop_item_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='商店购买流水';

-- -----------------------------------------------------------------------------
-- 统一操作记录表
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS operation_log (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    teacher_id      BIGINT          NOT NULL COMMENT '所属教师 ID',
    classroom_id    BIGINT          NOT NULL COMMENT '班级 ID',
    student_id      BIGINT          NOT NULL COMMENT '学生 ID',
    action_type     VARCHAR(20)     NOT NULL COMMENT '操作类型：SCORE / PURCHASE',
    ref_id          BIGINT          NOT NULL COMMENT '业务明细表主键',
    title           VARCHAR(120)    NOT NULL COMMENT '操作标题',
    summary         VARCHAR(255)    NOT NULL DEFAULT '' COMMENT '摘要文案',
    exp_change      INT             NOT NULL DEFAULT 0 COMMENT '经验变化',
    coin_change     INT             NOT NULL DEFAULT 0 COMMENT '金币变化',
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    reverted_at     DATETIME        NULL COMMENT '撤回时间',
    reverted_by     BIGINT          NULL COMMENT '撤回教师 ID',
    PRIMARY KEY (id),
    KEY idx_operation_log_teacher_id (teacher_id),
    KEY idx_operation_log_classroom_id (classroom_id),
    KEY idx_operation_log_student_id (student_id),
    KEY idx_operation_log_created_at (created_at)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='统一操作时间线';
