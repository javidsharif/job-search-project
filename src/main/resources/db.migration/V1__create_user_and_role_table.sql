create TABLE IF NOT EXISTS users (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(50) NOT NULL unique,
  date_of_birth DATE,
  phone VARCHAR(50) NOT NULL,
  gender VARCHAR(10),
  city VARCHAR(20),
  photo_url VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create TABLE IF NOT EXISTS roles (
  id BIGINT GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id SERIAL NOT NULL,
  role_id SERIAL NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO users (name, surname, password, email, phone, gender, city, photo_url)
VALUES ('John', 'Doe', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'admin@test.com', '+1234567890', 'Male', 'New York', null),
('Javid', 'Sharif', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'user@test.com', '+1234567890', 'Male', 'New York', null);

INSERT INTO roles(name)
  values ('ADMIN'),
  ('USER');

INSERT INTO users_roles(user_id, role_id)
  values (1, 1), (2, 2);
