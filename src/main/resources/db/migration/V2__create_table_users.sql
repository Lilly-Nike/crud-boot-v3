create table users (
    id bigint primary key auto_increment not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    age int not null,
    email varchar(255) not null,
    password varchar(255) not null
);
create unique index idx_unique_users_email on users(email);