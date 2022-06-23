insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');

-- pass = admin
insert into users (age, email, first_name, last_name, password)
values (33, 'admin@mail.ru', 'Admin', 'Adminovich', '$2a$12$0EiWfMwYT.xMrWP524oBku8qhhKv5K2qkCuIpfPcO2rcMaQ3jkzuG');

insert into users_roles (user_id, role_id) values (1, 1);