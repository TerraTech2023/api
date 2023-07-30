package com.terratech.api.controllers;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable("id") Long id) {
        UserResponse response = this.userService.findById(id);
        return new ResponseEntity<>(response, OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable("id") Long id, @RequestBody UserRequest user) {
        this.userService.update(id, user);
        return new ResponseEntity<>("SUCCESSFULLY UPDATED COLLECTOR", OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
        return new ResponseEntity<>("SUCCESSFULLY DELETED COLLECTOR",OK);
    }

}
