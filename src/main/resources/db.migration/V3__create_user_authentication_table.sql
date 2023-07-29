CREATE TABLE IF NOT EXISTS user_authentication (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  email VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT fk_user_authentication_user_id
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX idx_user_authentication_user_id ON user_authentication (user_id);