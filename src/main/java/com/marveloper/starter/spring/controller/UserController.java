package com.marveloper.starter.spring.controller;

import com.marveloper.starter.spring.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping(value = "")
    public User getUser(@RequestBody  User user) {
        return new User(user.getId(), UUID.randomUUID().toString());
    }
}
