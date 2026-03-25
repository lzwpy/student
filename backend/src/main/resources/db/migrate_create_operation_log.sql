-- 旧库补表：幂等（IF NOT EXISTS），由 JDBC URL 指定库名，勿写 USE
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
