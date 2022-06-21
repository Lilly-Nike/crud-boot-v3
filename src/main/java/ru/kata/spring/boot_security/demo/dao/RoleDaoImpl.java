package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> getRoleByName(String name) {
        return Optional.of(entityManager.createQuery("select r from Role as r where r.name = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult());
    }

    @Override
    public Set<Role> findAll() {
        return entityManager.createQuery("select r from Role as r", Role.class)
                .getResultList()
                .stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Optional<Role> findById(long id) {
        return Optional.of(entityManager.find(Role.class, id));
    }
}
