CREATE TABLE IF NOT EXISTS communication_tools (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  level VARCHAR(10)
);

INSERT INTO communication_tools(level)
VALUES ('telephone'),
('email');