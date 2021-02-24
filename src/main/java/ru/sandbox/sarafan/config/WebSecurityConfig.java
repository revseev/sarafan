package ru.sandbox.sarafan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error**", "/login**", "/H2-console/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement().disable() // todo remove after
                .csrf().disable()
                .headers(h -> h.frameOptions().disable()) // for H2-console
                .oauth2Login();
        // @formatter:on
    }
}
