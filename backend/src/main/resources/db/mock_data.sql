-- =============================================================================
-- 本地开发用 Mock 数据（在已执行 schema.sql 之后运行）
-- 登录测试账号：
--   teacher1 / password
--   teacher2 / password
-- 密码哈希：BCrypt，与 Spring Security BCryptPasswordEncoder 兼容
-- =============================================================================

USE pet_class;

SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 可选：需要「清空并重灌」时取消下面注释（仅开发库！）
-- -----------------------------------------------------------------------------
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE purchase_log;
-- TRUNCATE TABLE score_log;
-- TRUNCATE TABLE operation_log;
-- TRUNCATE TABLE shop_item;
-- TRUNCATE TABLE rule;
-- TRUNCATE TABLE pet;
-- TRUNCATE TABLE student_coin;
-- TRUNCATE TABLE student;
-- TRUNCATE TABLE classroom;
-- TRUNCATE TABLE teacher;
-- SET FOREIGN_KEY_CHECKS = 1;

-- 明文均为 password（哈希取自 Spring Security BCryptPasswordEncoderTests，与当前 encoder.matches 一致）
INSERT INTO teacher (id, username, password_hash, nickname, avatar)
VALUES
    (1, 'teacher1', '$2a$00$9N8N35BVs5TLqGL3pspAte5OWWA2a2aZIs.EGp7At7txYakFERMue', '张老师', ''),
    (2, 'teacher2', '$2a$00$9N8N35BVs5TLqGL3pspAte5OWWA2a2aZIs.EGp7At7txYakFERMue', '李老师', ''),
    (3, 'teacher3', '$2a$00$9N8N35BVs5TLqGL3pspAte5OWWA2a2aZIs.EGp7At7txYakFERMue', '王老师', '')
ON DUPLICATE KEY UPDATE
    password_hash = VALUES(password_hash),
    nickname      = VALUES(nickname);

INSERT INTO classroom (id, teacher_id, name)
VALUES
    (1, 1, '三年级一班'),
    (2, 1, '三年级二班'),
    (3, 2, '四年级实验班'),
    (4, 3, '五年级科创班')
ON DUPLICATE KEY UPDATE
    teacher_id = VALUES(teacher_id),
    name       = VALUES(name);

INSERT INTO student (id, classroom_id, name, sort_order)
VALUES
    (1, 1, '王小明', 1),
    (2, 1, '李小红', 2),
    (3, 1, '赵小刚', 3),
    (4, 2, '孙小美', 1),
    (5, 2, '周小华', 2),
    (6, 3, '吴小东', 1),
    (7, 1, '钱小乐', 4),
    (8, 1, '郑小楠', 5),
    (9, 2, '陈小可', 3),
    (10, 2, '林小宇', 4),
    (11, 3, '黄小文', 2),
    (12, 4, '刘小博', 1),
    (13, 4, '徐小艺', 2),
    (14, 4, '高小远', 3)
ON DUPLICATE KEY UPDATE
    classroom_id = VALUES(classroom_id),
    name         = VALUES(name),
    sort_order   = VALUES(sort_order);

INSERT INTO pet (id, student_id, name, image_key, level, exp, total_exp)
VALUES
    (1, 1, '咪咪', 'cat', 3, 120, 520),
    (2, 2, '豆豆', 'dog', 2, 40, 210),
    (3, 3, '球球', 'cat', 1, 10, 10),
    (4, 4, '飞飞', 'bird', 4, 200, 1200),
    (5, 5, '团团', 'cat', 2, 60, 260),
    (6, 6, '灵灵', 'rabbit', 1, -4, -4),
    (7, 7, '果果', 'dog', 2, 55, 280),
    (8, 8, '糖糖', 'cat', 1, 5, 5),
    (9, 9, '星星', 'bird', 3, 90, 400),
    (10, 10, '乐乐', 'rabbit', 2, 30, 150),
    (11, 11, '墨墨', 'cat', 2, 48, 220),
    (12, 12, '闪闪', 'dog', 3, 100, 600),
    (13, 13, '月月', 'bird', 1, 12, 12),
    (14, 14, '风风', 'rabbit', 2, 70, 310)
ON DUPLICATE KEY UPDATE
    name      = VALUES(name),
    image_key = VALUES(image_key),
    level     = VALUES(level),
    exp       = VALUES(exp),
    total_exp = VALUES(total_exp);

INSERT INTO student_coin (id, student_id, coins)
VALUES
    (1, 1, 80),
    (2, 2, 45),
    (3, 3, 20),
    (4, 4, 150),
    (5, 5, 30),
    (6, 6, 100),
    (7, 7, 62),
    (8, 8, 18),
    (9, 9, 95),
    (10, 10, 40),
    (11, 11, 55),
    (12, 12, 200),
    (13, 13, 33),
    (14, 14, 88)
ON DUPLICATE KEY UPDATE
    coins = VALUES(coins);

INSERT INTO rule (id, teacher_id, name, type, exp_value, coin_value, sort_order)
VALUES
    (1, 1, '积极举手', 'positive', 5, 2, 1),
    (2, 1, '作业全对', 'positive', 10, 5, 2),
    (3, 1, '帮助同学', 'positive', 8, 3, 3),
    (4, 1, '迟到', 'negative', -3, -1, 10),
    (5, 1, '课堂讲话', 'negative', -2, 0, 11),
    (6, 2, '发言精彩', 'positive', 6, 2, 1),
    (7, 1, '小组合作', 'positive', 6, 2, 4),
    (8, 1, '笔记工整', 'positive', 4, 1, 5),
    (9, 1, '未带作业', 'negative', -5, -2, 12),
    (10, 3, '实验报告优秀', 'positive', 12, 6, 1)
ON DUPLICATE KEY UPDATE
    teacher_id  = VALUES(teacher_id),
    name        = VALUES(name),
    type        = VALUES(type),
    exp_value   = VALUES(exp_value),
    coin_value  = VALUES(coin_value),
    sort_order  = VALUES(sort_order);

INSERT INTO score_log (id, student_id, classroom_id, rule_id, rule_name, exp_change, coin_change, operator_id, created_at)
VALUES
    (1, 1, 1, 1, '积极举手', 5, 2, 1, DATE_SUB(NOW(), INTERVAL 5 DAY)),
    (2, 1, 1, 2, '作业全对', 10, 5, 1, DATE_SUB(NOW(), INTERVAL 4 DAY)),
    (3, 2, 1, 3, '帮助同学', 8, 3, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (4, 3, 1, 4, '迟到', -3, -1, 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),
    (5, 4, 2, 2, '作业全对', 10, 5, 1, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (6, 5, 2, NULL, '手动调整', 0, 10, 1, NOW()),
    (7, 7, 1, 7, '小组合作', 6, 2, 1, DATE_SUB(NOW(), INTERVAL 6 HOUR)),
    (8, 8, 1, 8, '笔记工整', 4, 1, 1, DATE_SUB(NOW(), INTERVAL 5 HOUR)),
    (9, 12, 4, 10, '实验报告优秀', 12, 6, 3, DATE_SUB(NOW(), INTERVAL 3 HOUR)),
    (10, 14, 4, 1, '积极举手', 5, 2, 3, DATE_SUB(NOW(), INTERVAL 1 HOUR))
ON DUPLICATE KEY UPDATE
    student_id   = VALUES(student_id),
    classroom_id = VALUES(classroom_id),
    rule_id      = VALUES(rule_id),
    rule_name    = VALUES(rule_name),
    exp_change   = VALUES(exp_change),
    coin_change  = VALUES(coin_change),
    operator_id  = VALUES(operator_id);

INSERT INTO shop_item (id, teacher_id, name, description, image, price, stock, status)
VALUES
    (1, 1, '免作业券', '本周任选一次作业免做', '', 50, 10, 1),
    (2, 1, '小贴纸礼包', '随机贴纸一套', '', 20, -1, 1),
    (3, 1, '课间点歌', '下节课间播放一首歌', '', 30, 5, 1),
    (4, 1, '下架示例', '已下架商品', '', 10, 0, 0),
    (5, 2, '实验加分', '科学课实验表现加分记录', '', 40, -1, 1),
    (6, 1, '班长体验卡', '当一天班长助理', '', 35, 8, 1),
    (7, 3, '科创材料包', '简易电路小套件', '', 60, 3, 1),
    (8, 1, '表扬信', '班主任手写表扬信一封', '', 45, 5, 1)
ON DUPLICATE KEY UPDATE
    teacher_id  = VALUES(teacher_id),
    name        = VALUES(name),
    description = VALUES(description),
    image       = VALUES(image),
    price       = VALUES(price),
    stock       = VALUES(stock),
    status      = VALUES(status);

INSERT INTO purchase_log (id, student_id, shop_item_id, item_name, price, created_at)
VALUES
    (1, 1, 2, '小贴纸礼包', 20, DATE_SUB(NOW(), INTERVAL 2 DAY)),
    (2, 2, 1, '免作业券', 50, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (3, 4, 2, '小贴纸礼包', 20, NOW()),
    (4, 7, 6, '班长体验卡', 35, DATE_SUB(NOW(), INTERVAL 12 HOUR)),
    (5, 12, 7, '科创材料包', 60, DATE_SUB(NOW(), INTERVAL 6 HOUR)),
    (6, 14, 8, '表扬信', 45, DATE_SUB(NOW(), INTERVAL 2 HOUR))
ON DUPLICATE KEY UPDATE
    student_id   = VALUES(student_id),
    shop_item_id = VALUES(shop_item_id),
    item_name    = VALUES(item_name),
    price        = VALUES(price);

INSERT INTO operation_log (id, teacher_id, classroom_id, student_id, action_type, ref_id, title, summary, exp_change, coin_change, created_at, reverted_at, reverted_by)
VALUES
    (1, 1, 1, 1, 'SCORE', 1, '积极举手', 'EXP +5 ｜ 金币 +2', 5, 2, DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, NULL),
    (2, 1, 1, 1, 'SCORE', 2, '作业全对', 'EXP +10 ｜ 金币 +5', 10, 5, DATE_SUB(NOW(), INTERVAL 4 DAY), NULL, NULL),
    (3, 1, 1, 2, 'SCORE', 3, '帮助同学', 'EXP +8 ｜ 金币 +3', 8, 3, DATE_SUB(NOW(), INTERVAL 3 DAY), NULL, NULL),
    (4, 1, 1, 3, 'SCORE', 4, '迟到', 'EXP -3 ｜ 金币 -1', -3, -1, DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, NULL),
    (5, 1, 2, 4, 'SCORE', 5, '作业全对', 'EXP +10 ｜ 金币 +5', 10, 5, DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, NULL),
    (6, 1, 2, 5, 'SCORE', 6, '手动调整', 'EXP 0 ｜ 金币 +10', 0, 10, NOW(), NULL, NULL),
    (7, 1, 1, 1, 'PURCHASE', 1, '购买了小贴纸礼包', '消耗金币 20', 0, -20, DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, NULL),
    (8, 1, 1, 2, 'PURCHASE', 2, '购买了免作业券', '消耗金币 50', 0, -50, DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, NULL),
    (9, 1, 2, 4, 'PURCHASE', 3, '购买了小贴纸礼包', '消耗金币 20', 0, -20, NOW(), NULL, NULL),
    (10, 1, 1, 7, 'SCORE', 7, '小组合作', 'EXP +6 ｜ 金币 +2', 6, 2, DATE_SUB(NOW(), INTERVAL 6 HOUR), NULL, NULL),
    (11, 3, 4, 12, 'SCORE', 9, '实验报告优秀', 'EXP +12 ｜ 金币 +6', 12, 6, DATE_SUB(NOW(), INTERVAL 3 HOUR), NULL, NULL),
    (12, 3, 4, 14, 'PURCHASE', 6, '购买了表扬信', '消耗金币 45', 0, -45, DATE_SUB(NOW(), INTERVAL 2 HOUR), NULL, NULL)
ON DUPLICATE KEY UPDATE
    teacher_id   = VALUES(teacher_id),
    classroom_id = VALUES(classroom_id),
    student_id   = VALUES(student_id),
    action_type  = VALUES(action_type),
    ref_id       = VALUES(ref_id),
    title        = VALUES(title),
    summary      = VALUES(summary),
    exp_change   = VALUES(exp_change),
    coin_change  = VALUES(coin_change),
    reverted_at  = VALUES(reverted_at),
    reverted_by  = VALUES(reverted_by);

-- 与 AUTO_INCREMENT 对齐，避免后续自增与固定 ID 冲突
ALTER TABLE teacher AUTO_INCREMENT = 100;
ALTER TABLE classroom AUTO_INCREMENT = 100;
ALTER TABLE student AUTO_INCREMENT = 100;
ALTER TABLE pet AUTO_INCREMENT = 100;
ALTER TABLE student_coin AUTO_INCREMENT = 100;
ALTER TABLE rule AUTO_INCREMENT = 100;
ALTER TABLE score_log AUTO_INCREMENT = 100;
ALTER TABLE shop_item AUTO_INCREMENT = 100;
ALTER TABLE purchase_log AUTO_INCREMENT = 100;
ALTER TABLE operation_log AUTO_INCREMENT = 100;
