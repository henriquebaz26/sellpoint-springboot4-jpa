package com.sellpoint.SellPoint.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sellpoint.SellPoint.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findAll() {

        User u = new User(1L, "Henrique Baz", "henrique@gmail.com", "9999-9999", "senha123");

        return ResponseEntity.ok().body(u);
    }

}
