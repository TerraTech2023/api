package com.terratech.api.dto;

import com.terratech.api.model.Address;
import com.terratech.api.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public record UserRequest(
        String name,
        String email,
        String password,
        LocalDate dateOfBirth,
        String cep,
        String number
) {

    public UserRequest(User user) {
        this(user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getDateOfBirth(),
                user.getAddress().getCep(),
                user.getAddress().getNumber());
    }

    public User toUser() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .dateOfBirth(dateOfBirth)
                .address(new Address(cep, number))
                .residues(new ArrayList<>())
                .build();
    }

}
