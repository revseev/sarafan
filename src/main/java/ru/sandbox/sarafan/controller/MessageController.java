package ru.sandbox.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import ru.sandbox.sarafan.domain.Message;
import ru.sandbox.sarafan.domain.view.Views;
import ru.sandbox.sarafan.repository.MessageRepo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepo messageRepo;

    @GetMapping
    @JsonView(Views.IdText.class)
    public List<Message> getAll() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id")
                                  Message message) { // Магия! Spring находит по id из указанного пути нужный Message сам, т.е. неявно вызывается messageRepo.findById(id)
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb,
                          @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
