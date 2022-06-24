package ru.kata.spring.boot_security.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kata.spring.boot_security.demo.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtil {

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
