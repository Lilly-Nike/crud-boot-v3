create table roles (
    id bigint primary key auto_increment not null,
    name varchar(255) not null
);
create unique index idx_unique_roles_name on roles(name);