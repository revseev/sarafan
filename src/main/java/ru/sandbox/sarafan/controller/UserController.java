package ru.sandbox.sarafan.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sandbox.sarafan.domain.User;
import ru.sandbox.sarafan.repository.MessageRepo;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final MessageRepo messageRepo;

    @GetMapping()
    public Map<String, Object> getCurrentUserInfo(
            @AuthenticationPrincipal OidcUser user) { // todo try to make custom Service that deals with my User
        final HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("messages", messageRepo.findAll());
        return map;
    }
}
