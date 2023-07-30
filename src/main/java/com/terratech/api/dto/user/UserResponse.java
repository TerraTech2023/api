package com.terratech.api.dto.user;

import com.terratech.api.model.Address;
import com.terratech.api.model.Residue;
import com.terratech.api.model.User;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        String name,
        String email,
        String password,
        LocalDate dateOfBirth,
        Address address,
        List<Residue> residues
) {
    public UserResponse  (User user){
        this(user.getName(), user.getEmail(), user.getPassword(), user.getDateOfBirth(), user.getAddress(), user.getResidues());
    }
}
