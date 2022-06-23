package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.SecurityUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public void save(UserDto userDto) {
        var user = mapToUser(userDto);
        processBeforePersisted(user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        var user = mapToUser(userDto);
        processBeforePersisted(user);
        userDao.update(user);
        SecurityUtil.refreshRolesForAuthenticatedUser(user);
        return new UserDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var user = userDao.findById(id);
        userDao.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + email));
    }

    private User mapToUser(UserDto userDto) {
        var user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(
                (userDto.getRoles() == null)
                        ? Set.of()
                        : userDto.getRolesId().stream()
                            .map(Role::new)
                            .collect(Collectors.toSet())
        );
        return user;
    }

    private void processBeforePersisted(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            if (user.getId() != null) {
                var existUser = userDao.findById(user.getId());
                user.setPassword(existUser.getPassword());
            }
        }
        if (user.getRoles().isEmpty()) {
            var role = roleService.getRoleByName("ROLE_USER");
            user.setRoles(Set.of(role));
        } else {
            var roles = user.getRoles().stream()
                    .map(role -> roleService.findById(role.getId()))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
    }
}
