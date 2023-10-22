ALTER TABLE knowledge ADD COLUMN level_temp INT;

ALTER TABLE knowledge DROP COLUMN level;

ALTER TABLE knowledge RENAME level_temp TO level;