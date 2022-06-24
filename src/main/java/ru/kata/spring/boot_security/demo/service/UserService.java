package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    void save(UserDto user);
    UserDto update(UserDto user);
    void delete(Long id);
    UserDto getByEmail(String email);
    User getUserByEmail(String name);
}
