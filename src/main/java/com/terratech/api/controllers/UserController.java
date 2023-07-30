package com.terratech.api.controllers;

import com.terratech.api.dto.Message;
import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.dto.user.UserResponse;
import com.terratech.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private Message response;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable("id") Long id) {
        var response = this.userService.findById(id);
        return new ResponseEntity<>(response, OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Message> put(@PathVariable("id") Long id, @RequestBody UserRequest user) {
        this.response = new Message("SUCCESSFULLY UPDATED USER");
        this.userService.update(id, user);
        return new ResponseEntity<>(this.response, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id") Long id) {
        this.response = new Message("SUCCESSFULLY DELETED USER");
        this.userService.delete(id);
        return new ResponseEntity<>(this.response,OK);
    }

}
