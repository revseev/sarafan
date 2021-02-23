package ru.sandbox.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sandbox.sarafan.domain.Message;

//@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

}
