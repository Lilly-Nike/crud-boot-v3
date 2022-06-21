package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {
    Role getRoleByName(String name);
    Set<Role> findAll();
    Role findById(long id);
}
