package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.dto.UserWithAllRolesDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        var dtoUsers = userService.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoUsers);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        var user = userService.findById(id);
        var userDto = new UserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        var dtoUsers = roleService.findAll().stream()
                .map(RoleDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoUsers);
    }

    @GetMapping("{id}/roles")
    public ResponseEntity<UserWithAllRolesDto> getByIdAndAllRoles(@PathVariable Long id) {
        var user = userService.findById(id);
        var userDto = new UserDto(user);
        var dtoRoles = roleService.findAll().stream()
                .map(RoleDto::new)
                .collect(Collectors.toList());
        var response = new UserWithAllRolesDto(userDto, dtoRoles);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/email")
    public ResponseEntity<UserDto> getByEmail(@RequestBody String email) {
        var user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserDto user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto user) {
        var updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
