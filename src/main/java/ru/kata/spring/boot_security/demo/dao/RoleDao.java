package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleDao {
    Optional<Role> getRoleByName(String name);
    Set<Role> findAll();
    Optional<Role> findById(long id);
}
