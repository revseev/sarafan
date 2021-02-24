package ru.sandbox.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sandbox.sarafan.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {

}
