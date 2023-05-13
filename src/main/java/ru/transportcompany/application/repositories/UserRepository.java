package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.users.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}