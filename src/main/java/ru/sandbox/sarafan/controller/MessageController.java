package ru.sandbox.sarafan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sandbox.sarafan.exception.MessageNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("message")
public class MessageController {

    private final List<Map<String, String>> messages = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "First message");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "Second message");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "Third message");
        }});
    }};
    private AtomicLong counter = new AtomicLong(3L);


    @GetMapping
    public List<Map<String, String>> getAll() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return findMessage(id);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter.incrementAndGet()));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = findMessage(id);
        message.put("id", id);
        messageFromDb.putAll(message);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
         messages.remove(findMessage(id));
    }

    private Map<String, String> findMessage(String id) {
        return messages.stream()
                       .filter(m -> m.get("id").equals(id))
                       .findFirst()
                       .orElseThrow(MessageNotFoundException::new);
    }
}
