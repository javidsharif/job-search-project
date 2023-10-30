CREATE TABLE IF NOT EXISTS statuses (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title VARCHAR(50)
);

INSERT INTO statuses (title)
VALUES ('Checking!'),
('Successfully!'),
('Rejected!'),
('Placement has been suspended!');
