INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (age, email, first_name, last_name, password) VALUES
-- pass = admin
(33, 'admin@mail.ru', 'Admin', 'Adminovich', '$2a$12$0EiWfMwYT.xMrWP524oBku8qhhKv5K2qkCuIpfPcO2rcMaQ3jkzuG'),
-- pass = user
(33, 'user@mail.ru', 'User', 'Userovich', '$2a$12$Srx0Lj4svu5XZ3PqVhD..eh3oba5Hb0aRA8TBIm2FkYit5pcTHufa');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1), (1, 2), (2, 2);