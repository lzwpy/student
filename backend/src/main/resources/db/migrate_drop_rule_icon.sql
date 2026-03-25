-- 在已有库上删除 rule.icon（新库请直接执行 schema.sql，无需本脚本）
USE pet_class;
ALTER TABLE rule DROP COLUMN icon;
