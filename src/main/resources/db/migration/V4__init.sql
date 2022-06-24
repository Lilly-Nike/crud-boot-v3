INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

-- pass = admin
INSERT INTO users (age, email, first_name, last_name, password)
VALUES (33, 'admin@mail.ru', 'Admin', 'Adminovich', '$2a$12$0EiWfMwYT.xMrWP524oBku8qhhKv5K2qkCuIpfPcO2rcMaQ3jkzuG');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);