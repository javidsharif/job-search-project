ALTER TABLE education ADD COLUMN level_temp INT;

ALTER TABLE education DROP COLUMN level;

ALTER TABLE education RENAME level_temp TO level;