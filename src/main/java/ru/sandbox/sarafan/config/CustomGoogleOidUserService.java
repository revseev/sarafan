package ru.sandbox.sarafan.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import ru.sandbox.sarafan.domain.User;
import ru.sandbox.sarafan.repository.UserDetailsRepo;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomGoogleOidUserService extends OidcUserService {

    private final UserDetailsRepo userDetailsRepo;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String id = oidcUser.getAttribute("sub");
        User user = userDetailsRepo.findById(id)
                                   .orElseGet(() ->
                                           User.builder()
                                               .id(id)
                                               .name(oidcUser.getAttribute("name"))
                                               .email(oidcUser.getAttribute("email"))
                                               .userpic(oidcUser.getAttribute("picture"))
                                               .gender(oidcUser.getAttribute("gender"))
                                               .locale(oidcUser.getAttribute("locale"))
                                               .build());
        user.setLastVisit(LocalDateTime.now());
        userDetailsRepo.save(user);
        return oidcUser;
    }
}
