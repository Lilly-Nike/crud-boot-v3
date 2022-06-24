CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY pr_key_users_roles (user_id, role_id),
    FOREIGN KEY fk_users_role_to_users (user_id) REFERENCES users(id),
    FOREIGN KEY fk_users_role_to_roles (role_id) REFERENCES roles(id)
);