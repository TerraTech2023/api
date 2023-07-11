package com.terratech.api.controllers;

import com.terratech.api.dto.UserRequest;
import com.terratech.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), OK);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody UserRequest user) {
        return new ResponseEntity<>(userService.create(user), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody UserRequest user) {
        return new ResponseEntity<>(userService.update(id, user), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(OK);
    }

}
