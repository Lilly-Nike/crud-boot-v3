package ru.kata.spring.boot_security.demo.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kata.spring.boot_security.demo.model.User;

public final class SecurityUtil {

    private SecurityUtil() {

    }

    public static void refreshRolesForAuthenticatedUser(User user) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(user.getUsername())) {
            var token = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    user.getRoles()
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
