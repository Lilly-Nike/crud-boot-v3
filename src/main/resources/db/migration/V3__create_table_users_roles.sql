create table users_roles (
    user_id bigint not null,
    role_id bigint not null,
    primary key pr_key_users_roles (user_id, role_id),
    foreign key fk_users_role_to_users (user_id) references users(id),
    foreign key fk_users_role_to_roles (role_id) references roles(id)
);