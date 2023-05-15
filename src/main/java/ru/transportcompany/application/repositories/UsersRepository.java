package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.transportcompany.application.models.users.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
