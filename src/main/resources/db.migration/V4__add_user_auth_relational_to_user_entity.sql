ALTER TABLE users
ADD COLUMN user_authentication_id BIGINT,
ADD CONSTRAINT fk_user_authentication_id
  FOREIGN KEY (user_authentication_id)
  REFERENCES user_authentication(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;